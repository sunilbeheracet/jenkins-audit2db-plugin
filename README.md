
Date:16/JUN/2017

modified:   src/main/java/org/jenkins/plugins/audit2db/internal/DbAuditPublisherImpl.java
**********************************************************************************************
* making static to BuildDetailsHibernateRepository field for accecing repository from static method
    private static transient BuildDetailsHibernateRepository repository;

* add following two method for save BuildPromotion
	public static void saveBuildPromotions(BuildDetails buildDetails,String promotionId,String params){
    	try{
    		BuildPromotions buildPromotions=new BuildPromotionsImpl();
    		buildPromotions.setParams(params);
    		buildPromotions.setPromotionId(promotionId);
    		buildPromotions.setBuildDetails(buildDetails);
    		saveBuildPromotions(buildPromotions);
    	}catch(Exception e){
    		LOGGER.log(Level.SEVERE, e.getMessage(), e);
    	}
    	
    }
    private static void saveBuildPromotions(final BuildPromotions promotions) {
        if (null == promotions) {
    	    throw new IllegalArgumentException(
    		    "Invalid build promotions: cannot be null.");
    	}
        repository.getHibernateTemplate().save(promotions);
    }
    
...............................................................................................


modified:   src/main/java/org/jenkins/plugins/audit2db/internal/model/BuildDetailsImpl.java
**************************************************************************************
* Created two field for two column in BuildDetails 
	 private String parent; //including getter/setter
    private String buildNumber;//including getter/setter

* modify method resolveBuildParameters for saving BuildPromotions
	DbAuditPublisherImpl.saveBuildPromotions(this, this.buildNumber,buildVariables.toString());

* modify constructer public BuildDetailsImpl(final AbstractBuild<?, ?> build)  to add two column

	this.parent=Integer.toString(build.getRootBuild().getNumber());
	this.buildNumber=Integer.toString(build.getNumber());
	/* Condition for Promotion*/
	if(this.parent.equalsIgnoreCase(buildNumber) && !fullName.contains("promotion")){
		this.parent=null;
	}

-----------------------------------------------------------------------------------------------
new file:   src/main/java/org/jenkins/plugins/audit2db/internal/model/BuildPromotionsImpl.java
new file:   src/main/java/org/jenkins/plugins/audit2db/model/BuildPromotions.java
*****************************************************************************
* Create BuildPromotions file for JENKINS_BUILD_PROMOTION table


modified:   src/main/resources/hibernate.cfg.xml
*************************************************
*entry BuildPromotions POJO

