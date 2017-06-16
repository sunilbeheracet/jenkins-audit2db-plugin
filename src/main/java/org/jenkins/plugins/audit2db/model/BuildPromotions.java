package org.jenkins.plugins.audit2db.model;

/**
 * Data model to map build promotions.
 * 
 * @author Mindfire Solutions
 *
 */
public interface BuildPromotions {

	String getId();

	void setId(String id);

	String getParams();

	void setParams(String params);

	String getPromotionId();

	void setPromotionId(String promotionId);

	BuildDetails getBuildDetails();

	void setBuildDetails(BuildDetails buildDetails);

}
