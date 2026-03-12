package org.example.models;

public class Follow {
    private long followerId;
    private long followingId;
    private boolean isFollowing = false;

    public Follow(long followerId, long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.isFollowing = false;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(long followingId) {
        this.followingId = followingId;
    }

    public boolean isFollowing() {
        return isFollowing; 
    }

    public void toggleFollow() {
        isFollowing = !isFollowing;
    }


    @Override
    public String toString() {
        return "Follow [followerId=" + followerId + ", followingId=" + followingId + ", isFollowing=" + isFollowing + "]";
    }

}
