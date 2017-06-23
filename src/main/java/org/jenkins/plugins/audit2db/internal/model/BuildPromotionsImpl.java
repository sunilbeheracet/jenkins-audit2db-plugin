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
@Entity(name="JENKINS_PROMOTION_PARAMS")
public class BuildPromotionsImpl implements BuildPromotions {
	
	private Integer id;
    private String params;
    private Integer promotionNumber;
    private Integer buildNumber;
    private BuildDetails buildDetails;
    
    public BuildPromotionsImpl(){
    	super();
    }
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getId()
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true)
    @Override
    public Integer getId() {
        return id;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setId(java.lang.String)
     */
    @Override
    public void setId(final Integer id) {
        this.id = id;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getParameters()
     */
    @Column(nullable=false, unique=false)
    @Override
    public String getParameters() {
        return params;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setParameters(java.lang.String)
     */
    @Override
    public void setParameters(final String parameters) {
        this.params = parameters;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getPromotionNumber()
     */
    @Column(nullable=true, unique=false)
    @Override
    public Integer getPromotionNumber() {
        return promotionNumber;
    }

    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setPromotionNumber(Integer)
     */
    @Override
    public void setPromotionNumber(final Integer promotionNumber) {
        this.promotionNumber = promotionNumber;
    }
    
    /**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getBuildDetails()
     */
    @ManyToOne(targetEntity=BuildDetailsImpl.class)
    @JoinColumn(name="buildDetailsId",nullable=false, unique=false)
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
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#getBuildNumber()
     */
    @Override
    @Column(nullable=true, unique=false)
	public Integer getBuildNumber() {
		return buildNumber;
	}

	/**
     * @see org.jenkins.plugins.audit2db.model.BuildPromotions#setBuildNumber(Integer)
     */
    @Override
	public void setBuildNumber(Integer buildNumber) {
		this.buildNumber = buildNumber;
	}
    
}
