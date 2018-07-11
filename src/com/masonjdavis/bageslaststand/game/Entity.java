package com.masonjdavis.bageslaststand.game;

public abstract class Entity implements Drawable {
    private Location location;

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setX(float x) {
        this.location.setX(x);
    }

    public void setY(float y) {
        this.location.setY(y);
    }

    public float getX() {
        return this.location.getX();
    }

    public float getY() {
        return this.location.getY();
    }
}
