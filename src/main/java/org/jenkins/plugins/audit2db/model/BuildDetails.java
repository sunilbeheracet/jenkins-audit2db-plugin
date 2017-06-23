/**
 * 
 */
package org.jenkins.plugins.audit2db.model;

import java.util.Date;
import java.util.List;

/**
 * Data model to map build details.
 * 
 * @author Marco Scata
 * 
 */
public interface BuildDetails {
    Integer getId();

    void setId(Integer id);

   /* String getName();

    void setName(String name);*/

//    String getFullName();

//    void setFullName(String fullName);

    Date getStartDate();

    void setStartDate(Date start);

    Date getEndDate();

    void setEndDate(Date end);

    Long getDuration();

    void setDuration(Long duration);

    String getResult();

    void setResult(String result);

    String getUserId();

    void setUserId(String userId);

    String getUserName();

    void setUserName(String userName);

    /*List<BuildParameter> getParameters();

    void setParameters(List<BuildParameter> params);*/

    BuildNode getNode();

    void setNode(BuildNode node);

	/*String getParent();

	void setParent(String parent);*/

	Integer getBuildNumber();

	void setBuildNumber(Integer buildNumber);

	String getBuildId();

	void setBuildId(String buildId);

	BuildJob getJob();

	void setJob(BuildJob job);

}
