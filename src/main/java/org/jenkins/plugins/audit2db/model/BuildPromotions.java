package org.jenkins.plugins.audit2db.model;

/**
 * Data model to map build promotions.
 * 
 * @author Mindfire Solutions
 *
 */
public interface BuildPromotions {

	Integer getId();

	void setId(Integer id);

	String getParameters();

	void setParameters(String parameters);

	Integer getPromotionNumber();

	void setPromotionNumber(Integer promotionNumber);

	BuildDetails getBuildDetails();

	void setBuildDetails(BuildDetails buildDetails);

	Integer getBuildNumber();

	void setBuildNumber(Integer buildNumber);

}
