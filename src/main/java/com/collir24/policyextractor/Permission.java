package com.collir24.policyextractor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Permission {

	private final String permission;
	private final String target;
	private final List<String> actions;

	/**
	 * 
	 * @param permission
	 * @param target
	 */
	public Permission(String permission, String target) {
		super();
		this.permission = permission;
		this.target = target;
		this.actions = Collections.emptyList();
	}

	/**
	 * 
	 * @param permission
	 * @param target
	 * @param actions
	 */
	public Permission(String permission, String target, String actions) {
		super();
		this.permission = permission;
		this.target = target;
		String[] actionsArray = actions.split(",");
		this.actions = Arrays.asList(actionsArray);
	}

	public String getPermission() {
		return permission;
	}

	public String getTarget() {
		return target;
	}

	public List<String> getActions() {
		return actions;
	}

	@Override
	public String toString() {
		return "Permission [permission=" + permission + ", target=" + target
				+ ", actions=" + actions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result
				+ ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
