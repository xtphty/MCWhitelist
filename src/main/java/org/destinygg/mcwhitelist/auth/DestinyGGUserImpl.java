/**
 * 
 */
package org.destinygg.mcwhitelist.auth;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 * {@link CachedAuthUser} implementation for storing a destiny.gg user's
 * authenticated data, and providing necessary validation
 * 
 * @author xtphty
 *
 */
public class DestinyGGUserImpl implements CachedAuthUser {
	private String login;
	public Long subscriptionEndTimestamp;
	public Long cacheEndTimestamp;
	public Long lastAuthTimestamp;
	public Long lastRefreshTimestamp;
	public String mcName;
	public String mcUUID;

	public DestinyGGUserImpl(String login, Long subscriptionEnd) {
		this.login = login;
		this.subscriptionEndTimestamp = subscriptionEnd;
		this.lastAuthTimestamp = System.currentTimeMillis();
		this.resetCacheTimestamp();
		this.lastRefreshTimestamp = null;

	}

	@Override
	public String getLoginId() {
		return login;
	}

	@Override
	public String getMCName() {
		return mcName;
	}

	@Override
	public void setMCName(String mcName) {
		this.mcName = mcName;
	}

	@Override
	public String getMCUUID() {
		return mcUUID;
	}

	@Override
	public void setMCUUID(String mcUUID) {
		this.mcUUID = mcUUID;
	}

	@Override
	public boolean isValid() {
		return StringUtils.isNotBlank(login) && StringUtils.length(mcName) >= 4 && StringUtils.length(mcName) <= 16
				&& StringUtils.length(mcUUID) == 36;
	}

	@Override
	public boolean isSubscriptionExpired() {
		return TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - this.subscriptionEndTimestamp) > 0;
	}

	@Override
	public Long getSubscriptionEndTimestamp() {
		return this.subscriptionEndTimestamp;
	}

	@Override
	public void setSubscriptionEndTimestamp(Long timestamp) {
		this.subscriptionEndTimestamp = timestamp;
	}

	@Override
	public void resetCacheTimestamp() {
		this.cacheEndTimestamp = Math.min(this.subscriptionEndTimestamp, TimeUnit.DAYS.toMillis(1));
		this.lastRefreshTimestamp = System.currentTimeMillis();
	}

	@Override
	public boolean isCacheExpired() {
		return TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - this.cacheEndTimestamp) > 0;
	}

	@Override
	public Long getLastAuthTimestamp() {
		return this.lastAuthTimestamp;
	}

	@Override
	public Long getLastRefreshTimestamp() {
		return this.lastRefreshTimestamp;
	}
}
