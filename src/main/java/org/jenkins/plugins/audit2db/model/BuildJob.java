package org.jenkins.plugins.audit2db.model;

/**
 * Data model to map build job details.
 * 
 * @author Mindfire Solutions
 *
 */
public interface BuildJob {

	Integer getId();

	void setId(Integer id);

	Integer getPromotionNumber();

	void setPromotionNumber(Integer promotionNumber);

	Integer getBuildNumber();

	void setBuildNumber(Integer buildNumber);

	String getName();

	void setName(String name);

}
