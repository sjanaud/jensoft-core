/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray;

import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.ray.painter.draw.AbstractRayDraw;
import org.jensoft.core.plugin.ray.painter.effect.AbstractRayEffect;
import org.jensoft.core.plugin.ray.painter.fill.AbstractRayFill;

/**
 * Ray Group defines common properties for ray's members.<br>
 * <ul>
 * <li>RayNature</li>
 * <li>RayBase</li>
 * <li>ThicknessType</li>
 * <li>Ray thickness</li>
 * <li>RayDraw</li>
 * <li>RayFill</li>
 * <li>RayEffect</li>
 * </ul>
 * <br>
 * <br>
 * other properties like, ray value and ascent/descent value should be set on
 * each ray's members.
 */
public class RayGroup extends Ray {

	/** ray nature, XRay or YRay for this group */
	private RayNature groupNature;

	/** flag ray nature set */
	private boolean groupNatureSet;

	/** draw for all ray's member of this group */
	private AbstractRayDraw groupDraw;

	/** flag ray draw set */
	private boolean groupDrawSet;

	/** fill for all ray's member of this group */
	private AbstractRayFill groupFill;

	/** flag ray fill set */
	private boolean groupFillSet;

	/** effect for all ray's member of this group */
	private AbstractRayEffect groupEffect;

	/** flag ray effect set */
	private boolean groupEffectSet;

	/** base for all ray's member of this group */
	private double groupBase;

	/** flag ray base set */
	private boolean groupBaseSet;

	/** thickness type for all ray's member of this group */
	private ThicknessType groupThicknessType;

	/** flag ray nature set */
	private boolean groupThicknessTypeSet;

	/** thickness for all ray's member of this group */
	private double groupThickness;

	/** flag ray thickness set */
	private boolean groupThicknessSet;

	/** rays members */
	private List<Ray> rays = new ArrayList<Ray>();

	/**
	 * create a new ray group.
	 */
	public RayGroup() {
	}

	/**
	 * copy all set properties to ray's members
	 */
	public void copyToRays() {
		if (isGroupBaseSet()) {
			copyBase();
		}
		if (isGroupDrawSet()) {
			copyDraw();
		}
		if (isGroupFillSet()) {
			copyFill();
		}
		if (isGroupEffectSet()) {
			copyEffect();
		}
		if (isGroupNatureSet()) {
			copyNature();
		}
		if (isGroupThicknessTypeSet()) {
			copyThiknessType();
		}
		if (isGroupThicknessSet()) {
			copyThickness();
		}

	}

	/**
	 * copy thickness type to ray's members
	 */
	public void copyThiknessType() {
		for (Ray ray : rays) {
			ray.setThicknessType(groupThicknessType);
		}
	}

	/**
	 * copy thickness to ray's members
	 */
	public void copyThickness() {
		for (Ray ray : rays) {
			ray.setThickness(groupThickness);
		}
	}

	/**
	 * copy base to ray's members
	 */
	public void copyBase() {
		for (Ray ray : rays) {
			ray.setRayBase(groupBase);
		}
	}

	/**
	 * copy nature to ray's members
	 */
	public void copyNature() {
		for (Ray ray : rays) {
			ray.setRayNature(groupNature);
		}
	}

	/**
	 * copy draw to ray's members
	 */
	public void copyDraw() {
		for (Ray ray : rays) {
			ray.setRayDraw(groupDraw);
		}
	}

	/**
	 * copy fill to ray's members
	 */
	public void copyFill() {
		for (Ray ray : rays) {
			ray.setRayFill(groupFill);
		}
	}

	/**
	 * copy effect to ray's members
	 */
	public void copyEffect() {
		for (Ray ray : rays) {
			ray.setRayEffect(groupEffect);
		}
	}

	/**
	 * @return the groupNature
	 */
	public RayNature getGroupNature() {
		return groupNature;
	}

	/**
	 * @param groupNature
	 *            the groupNature to set
	 */
	public void setGroupNature(RayNature groupNature) {
		this.groupNature = groupNature;
		setGroupNatureSet(true);
	}

	/**
	 * @return the groupDraw
	 */
	public AbstractRayDraw getGroupDraw() {
		return groupDraw;
	}

	/**
	 * @param groupDraw
	 *            the groupDraw to set
	 */
	public void setGroupDraw(AbstractRayDraw groupDraw) {
		this.groupDraw = groupDraw;
		setGroupDrawSet(true);
	}

	/**
	 * @return the groupFill
	 */
	public AbstractRayFill getGroupFill() {
		return groupFill;
	}

	/**
	 * @param groupFill
	 *            the groupFill to set
	 */
	public void setGroupFill(AbstractRayFill groupFill) {
		this.groupFill = groupFill;
		setGroupFillSet(true);
	}

	/**
	 * @return the groupEffect
	 */
	public AbstractRayEffect getGroupEffect() {
		return groupEffect;
	}

	/**
	 * @param groupEffect
	 *            the groupEffect to set
	 */
	public void setGroupEffect(AbstractRayEffect groupEffect) {
		this.groupEffect = groupEffect;
		setGroupEffectSet(true);
	}

	/**
	 * @return the groupBase
	 */
	public double getGroupBase() {
		return groupBase;
	}

	/**
	 * @param groupBase
	 *            the groupBase to set
	 */
	public void setGroupBase(double groupBase) {
		this.groupBase = groupBase;
		setGroupBaseSet(true);
	}

	/**
	 * @return the groupThicknessType
	 */
	public ThicknessType getGroupThicknessType() {
		return groupThicknessType;
	}

	/**
	 * @param groupThicknessType
	 *            the groupThicknessType to set
	 */
	public void setGroupThicknessType(ThicknessType groupThicknessType) {
		this.groupThicknessType = groupThicknessType;
		setGroupThicknessTypeSet(true);
	}

	/**
	 * @return the groupThickness
	 */
	public double getGroupThickness() {
		return groupThickness;
	}

	/**
	 * @param groupThickness
	 *            the groupThickness to set
	 */
	public void setGroupThickness(double groupThickness) {
		this.groupThickness = groupThickness;
		setGroupThicknessSet(true);
	}

	/**
	 * add ray
	 * 
	 * @param ray
	 *            the ray to add
	 */
	public void addRay(Ray ray) {
		if (ray instanceof RayGroup) {
			throw new IllegalArgumentException("Group can not be contain other group.");
		}
		rays.add(ray);
	}

	/**
	 * remove ray
	 * 
	 * @param ray
	 *            the ray to remove
	 */
	public void removeRay(Ray ray) {
		rays.remove(ray);
	}

	/**
	 * get ray registry
	 * 
	 * @return ray registry
	 */
	public List<Ray> getRays() {
		return rays;
	}

	/**
	 * set ray registry
	 * 
	 * @param rays
	 */
	public void setRays(List<Ray> rays) {
		this.rays = rays;
	}

	/**
	 * @return the groupNatureSet
	 */
	public boolean isGroupNatureSet() {
		return groupNatureSet;
	}

	/**
	 * @param groupNatureSet
	 *            the groupNatureSet to set
	 */
	public void setGroupNatureSet(boolean groupNatureSet) {
		this.groupNatureSet = groupNatureSet;
	}

	/**
	 * @return the groupDrawSet
	 */
	public boolean isGroupDrawSet() {
		return groupDrawSet;
	}

	/**
	 * @param groupDrawSet
	 *            the groupDrawSet to set
	 */
	public void setGroupDrawSet(boolean groupDrawSet) {
		this.groupDrawSet = groupDrawSet;
	}

	/**
	 * @return the groupFillSet
	 */
	public boolean isGroupFillSet() {
		return groupFillSet;
	}

	/**
	 * @param groupFillSet
	 *            the groupFillSet to set
	 */
	public void setGroupFillSet(boolean groupFillSet) {
		this.groupFillSet = groupFillSet;
	}

	/**
	 * @return the groupEffectSet
	 */
	public boolean isGroupEffectSet() {
		return groupEffectSet;
	}

	/**
	 * @param groupEffectSet
	 *            the groupEffectSet to set
	 */
	public void setGroupEffectSet(boolean groupEffectSet) {
		this.groupEffectSet = groupEffectSet;
	}

	/**
	 * @return the groupBaseSet
	 */
	public boolean isGroupBaseSet() {
		return groupBaseSet;
	}

	/**
	 * @param groupBaseSet
	 *            the groupBaseSet to set
	 */
	public void setGroupBaseSet(boolean groupBaseSet) {
		this.groupBaseSet = groupBaseSet;
	}

	/**
	 * @return the groupThicknessTypeSet
	 */
	public boolean isGroupThicknessTypeSet() {
		return groupThicknessTypeSet;
	}

	/**
	 * @param groupThicknessTypeSet
	 *            the groupThicknessTypeSet to set
	 */
	public void setGroupThicknessTypeSet(boolean groupThicknessTypeSet) {
		this.groupThicknessTypeSet = groupThicknessTypeSet;
	}

	/**
	 * @return the groupThicknessSet
	 */
	public boolean isGroupThicknessSet() {
		return groupThicknessSet;
	}

	/**
	 * @param groupThicknessSet
	 *            the groupThicknessSet to set
	 */
	public void setGroupThicknessSet(boolean groupThicknessSet) {
		this.groupThicknessSet = groupThicknessSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.ray.Ray#setRayDraw(org.jensoft.core.plugin.ray
	 * .painter.draw.AbstractRayDraw)
	 */
	@Override
	public void setRayDraw(AbstractRayDraw rayDraw) {
		setGroupDraw(rayDraw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.ray.Ray#setRayFill(org.jensoft.core.plugin.ray
	 * .painter.fill.AbstractRayFill)
	 */
	@Override
	public void setRayFill(AbstractRayFill rayFill) {
		setGroupFill(rayFill);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.ray.Ray#setRayEffect(org.jensoft.core.plugin.
	 * ray.painter.effect.AbstractRayEffect)
	 */
	@Override
	public void setRayEffect(AbstractRayEffect rayEffect) {
		setGroupEffect(rayEffect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.plugin.ray.Ray#setThickness(double)
	 */
	@Override
	public void setThickness(double thickness) {
		setGroupThickness(thickness);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.ray.Ray#setThicknessType(org.jensoft.core.plugin
	 * .ray.Ray.ThicknessType)
	 */
	@Override
	public void setThicknessType(ThicknessType thicknessType) {
		setGroupThicknessType(thicknessType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.ray.Ray#setRayNature(org.jensoft.core.plugin.
	 * ray.Ray.RayNature)
	 */
	@Override
	public void setRayNature(RayNature rayNature) {
		setGroupNature(rayNature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.plugin.ray.Ray#setRayBase(double)
	 */
	@Override
	public void setRayBase(double rayBase) {
		setGroupBase(rayBase);
	}

}
