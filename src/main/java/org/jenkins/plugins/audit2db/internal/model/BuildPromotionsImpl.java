package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildPromotions;

/**
 * Data class for build promotions.
 * 
 * @author Mindfire Solutions
 *
 */
@Entity(name="JENKINS_BUILD_PROMOTIONS")
public class BuildPromotionsImpl implements BuildPromotions {
	
	private String id;
    private String params;
    private String promotionId;
    private String buildId;
    private String jobName;
    private BuildDetails buildDetails;
    
    public BuildPromotionsImpl(){
    	super();
    }
    
    public BuildPromotionsImpl(final String id, final String params, final String promotionId, final BuildDetails buildDetails) {
    	this.id = id;
        this.params = params;
        this.promotionId = promotionId;
        this.buildDetails = buildDetails;
   }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getId()
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true)
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setId(java.lang.String)
     */
    @Override
    public void setId(final String id) {
        this.id = id;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getParams()
     */
    @Column(nullable=false, unique=false)
    @Override
    public String getParams() {
        return params;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setParams(java.lang.String)
     */
    @Override
    public void setParams(final String params) {
        this.params = params;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getPromotionId()
     */
    @Column(name="promotion_id",nullable=true, unique=false)
    @Override
    public String getPromotionId() {
        return promotionId;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setPromotionId(java.lang.String)
     */
    @Override
    public void setPromotionId(final String promotionId) {
        this.promotionId = promotionId;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getBuildDetails()
     */
    @ManyToOne(targetEntity=BuildDetailsImpl.class)
    @JoinColumn(name="buildDetails_id",nullable=false, unique=false)
    @Override
    public BuildDetails getBuildDetails() {
        return buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setBuildDetails(java.lang.String)
     */
    @Override
    public void setBuildDetails(final BuildDetails buildDetails) {
        this.buildDetails = buildDetails;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getBuildId()
     */
    @Override
    @Column(name="build_id",nullable=true, unique=false)
	public String getBuildId() {
		return buildId;
	}

	/**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setBuildId(java.lang.String)
     */
    @Override
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getJobName()
     */
    @Override
    @Column(name="job_name",nullable=true, unique=false)
	public String getJobName() {
		return jobName;
	}
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setJobName(java.lang.String)
     */
    @Override
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
    
}
