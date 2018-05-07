package com.real.conetserver.tunnel.model;

/**
 * @author asuis
 */
public class TunnelMessageContent {
    private String receiverId;
    private String content;

    public TunnelMessageContent() {
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public String getContent() {
        return this.content;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TunnelMessageContent)) return false;
        final TunnelMessageContent other = (TunnelMessageContent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$receiverId = this.getReceiverId();
        final Object other$receiverId = other.getReceiverId();
        if (this$receiverId == null ? other$receiverId != null : !this$receiverId.equals(other$receiverId))
            return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $receiverId = this.getReceiverId();
        result = result * PRIME + ($receiverId == null ? 43 : $receiverId.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof TunnelMessageContent;
    }

    public String toString() {
        return "TunnelMessageContent(receiverId=" + this.getReceiverId() + ", content=" + this.getContent() + ")";
    }
}
