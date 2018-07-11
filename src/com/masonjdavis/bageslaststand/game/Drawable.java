package com.masonjdavis.bageslaststand.game;

import java.io.File;

public interface Drawable {

    File getTexture();

    Location getLocation();

    void setLocation(Location location);
}
