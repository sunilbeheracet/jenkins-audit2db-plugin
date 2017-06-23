/**
 * 
 */
package org.jenkins.plugins.audit2db.internal;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jenkins.plugins.audit2db.DbAuditPublisher;
import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.data.BuildDetailsHibernateRepository;
import org.jenkins.plugins.audit2db.internal.data.HibernateUtil;
import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildJobImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildPromotionsImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildJob;
import org.jenkins.plugins.audit2db.model.BuildPromotions;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Marco Scata
 * 
 */
public class DbAuditPublisherImpl extends Notifier implements DbAuditPublisher {
    private final static Logger LOGGER = Logger
    .getLogger(DbAuditPublisherImpl.class.getName());

    // must be transient or it will be serialised in the job config
    private transient BuildDetailsHibernateRepository repository;

    @Override
    public BuildDetailsRepository getRepository() {
	if (null == repository) {
	    repository = new BuildDetailsHibernateRepository(
		    getSessionFactory());
	}
	return repository;
    }

    /**
     * Default constructor annotated as data-bound is needed to load up the
     * saved xml configuration values.
     */
    @DataBoundConstructor
    public DbAuditPublisherImpl() {
    }

    /**
     * The annotated descriptor will hold and display the configuration info. It
     * doesn't have to be an inner class, as most sample plugins seem to
     * suggest.
     */
    @Extension
    public final static DbAuditPublisherDescriptorImpl descriptor = new DbAuditPublisherDescriptorImpl(
	    DbAuditPublisherImpl.class);

    @Override
    public BuildStepDescriptor<Publisher> getDescriptor() {
	LOGGER.log(Level.FINE, "Retrieving descriptor");
	return descriptor;
    }

    /**
     * @see hudson.tasks.Notifier#needsToRunAfterFinalized()
     */
    @Override
    public boolean needsToRunAfterFinalized() {
	// run even after the build is marked as complete
	return true;
    }

    /**
     * @see hudson.tasks.BuildStep#getRequiredMonitorService()
     */
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
	return BuildStepMonitor.NONE;
    }

    public static SessionFactory getSessionFactory() {
	final Properties props = HibernateUtil.getExtraProperties(
		descriptor.getJdbcDriver(), descriptor.getJdbcUrl(),
		descriptor.getJdbcUser(), descriptor.getJdbcPassword());

	return HibernateUtil.getSessionFactory(props);
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build,
	    final Launcher launcher, final BuildListener listener)
    throws InterruptedException, IOException {

	LOGGER.log(
		Level.FINE,
		String.format("perform: %s; launcher: %s",
			build.getDisplayName(), launcher.toString()));
	/*final BuildDetails details = getRepository().getBuildDetailsForBuild(
		build);*/
	boolean result = false;
	try {
		BuildJob buildJob=getRepository().getBuildJobByName(build.getRootBuild().getProject().getDisplayName());
		BuildDetails details=getRepository().getBuildDetails(buildJob.getId(),build.getNumber() );
		if(details!=null){
			details.setDuration(build.getDuration());
			details.setEndDate(new Date(details.getStartDate().getTime()
				+ details.getDuration()));
			details.setResult(build.getResult().toString());
	
		   getRepository().updateBuildDetails(details);
		 LOGGER.log(Level.FINE,
		    "Updated build details with id=" + details.getId());
	    result = super.perform(build, launcher, listener);
		}
	} catch (final Throwable t) {
		System.out.println(" perform problem :");
		t.printStackTrace();
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}

	return result;
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build,
	    final BuildListener listener) {
	LOGGER.log(Level.FINE,
		String.format("prebuild: %s;", build.getDisplayName()));
	
	Object id = null;
	try {
		getRepository();
		if(build.getFullDisplayName().contains("promotion")){
			if(build.getBuildVariables()!=null){
				JSONObject json=new JSONObject();
				json.putAll(build.getBuildVariables());
				if(!json.isEmpty()){
					BuildJob buildJob=getRepository().getBuildJobByName(build.getRootBuild().getProject().getDisplayName());
					if(buildJob!=null){
						BuildDetails buildDetails=getRepository().getBuildDetails(buildJob.getId(),build.getRootBuild().getNumber() );
						if(buildDetails!=null){
							saveBuildPromotions(buildDetails,build.getRootBuild().getNumber(),json.toString(),build.getNumber());
							id=buildDetails.getId();
						}
					}
				}
			}
		}else{
			BuildJob buildJob=saveJobDetails(build.getRootBuild().getProject().getDisplayName(),build.getRootBuild().getNumber());
			if(buildJob!=null && buildJob.getId()!=null){
				final BuildDetails details = new BuildDetailsImpl(build);
				details.setJob(buildJob);
				id = getRepository().saveBuildDetails(details);
			}
		}
		LOGGER.log(Level.INFO, "Saved build details with id====="+id);
	    LOGGER.log(Level.FINE, "Saved build details with id=" + id);
	} catch (final Throwable t) {
		t.printStackTrace();
	    LOGGER.log(Level.SEVERE, t.getMessage(), t);
	}
	return ((super.prebuild(build, listener)) && (id != null));
    }
   
    private void saveBuildPromotions(BuildDetails buildDetails,Integer parentBuildNumber,String params,Integer promotionNumber){
    	try{
    		BuildPromotions buildPromotions=new BuildPromotionsImpl();
    		buildPromotions.setParameters(params);
    		buildPromotions.setPromotionNumber(promotionNumber);
    		buildPromotions.setBuildDetails(buildDetails);
    		buildPromotions.setBuildNumber(parentBuildNumber);
    		saveBuildPromotions(buildPromotions);
    		updateJobDetails(promotionNumber,buildDetails.getJob().getName(),parentBuildNumber);
    	}catch(Exception e){
    		LOGGER.log(Level.SEVERE, e.getMessage(), e);
    	}
    	
    }
    private void saveBuildPromotions(final BuildPromotions promotions) {
        if (null == promotions) {
    	    throw new IllegalArgumentException(
    		    "Invalid build promotions: cannot be null.");
    	}
        repository.getHibernateTemplate().save(promotions);
    }
    private BuildJob saveJobDetails(String name,Integer buildNumber){
    	LOGGER.log(Level.INFO, "Inside @method: saveJobDetails");
    	try{
    		/** Check whether the job exist or not **/
    		BuildJob buildJob=repository.getBuildJobByName(name);
    		if(buildJob==null){
	    		/** Save Build Promotion Details **/
	    		buildJob=new BuildJobImpl();
	    		buildJob.setName(name);
	    		buildJob.setBuildNumber(buildNumber);
	    		Integer id=(Integer) repository.getHibernateTemplate().save(buildJob);
	    		buildJob.setId(id);
    		}else{
    			int oldBuildNumber=buildJob.getBuildNumber();
    			int newBuildNumber=buildNumber;
    			if(oldBuildNumber<=newBuildNumber){
    				buildJob.setBuildNumber(buildNumber);
    				buildJob.setPromotionNumber(null);
    			}
    		}
    		return buildJob;
    	}catch(Exception e){
    		LOGGER.log(Level.SEVERE,"Problem occurs when save or update BuildPromotionDetails",e);
    		return null;
    	}
    }
   
    private void updateJobDetails(final Integer promotionNumber,final String name,final Integer buildNumber){
    	LOGGER.log(Level.INFO, "Inside @method: updatePromotionDetails");
    	try{
	    	BuildJob buildJob=repository.getBuildJobByName(name);
	    	if(buildJob!=null){
	    		int oldBuildNumber=buildJob.getBuildNumber();
	    		int newBuildNumber=buildNumber;
	    		if(oldBuildNumber<=newBuildNumber){
	    			buildJob.setBuildNumber(buildNumber);
	    			buildJob.setPromotionNumber(promotionNumber);
	    		}
	    		repository.getHibernateTemplate().update(buildJob);
	    	}else{
	    		throw new IllegalArgumentException(
	        		    "Invalid build name: "+name);
	    	}
    	}catch(Exception e){
    		LOGGER.log(Level.SEVERE,"Problem occurs when update BuildPromotionDetails",e);
    	}
    }
}
