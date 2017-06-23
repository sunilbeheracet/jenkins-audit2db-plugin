package org.jenkins.plugins.audit2db.internal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jenkins.plugins.audit2db.model.BuildJob;
/**
 * Data class for build job details.
 * 
 * @author Mindfire Solutions
 *
 */
@Entity(name="JENKINS_BUILD_JOB")
public class BuildJobImpl implements BuildJob {
	
	private Integer id;
    private Integer promotionNumber;
    private Integer buildNumber;
    private String name;
	
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#getId()
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true)
    @Override
    public Integer getId() {
		return id;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#setId(Integer)
     */
    @Override
	public void setId(Integer id) {
		this.id = id;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#getPromotionNumber()
     */
    @Override
    @Column(nullable=true, unique=false)
	public Integer getPromotionNumber() {
		return promotionNumber;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#setPromotionNumber(Integer)
     */
    @Override
	public void setPromotionNumber(Integer promotionNumber) {
		this.promotionNumber = promotionNumber;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#getBuildNumber()
     */
    @Override
    @Column(nullable=false, unique=false)
	public Integer getBuildNumber() {
		return buildNumber;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#setBuildNumber(Integer)
     */
    @Override
	public void setBuildNumber(Integer buildNumber) {
		this.buildNumber = buildNumber;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#getName()
     */
    @Override
    @Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildJob#setName(java.lang.String)
     */
    @Override
	public void setName(String name) {
		this.name = name;
	}
    
}
