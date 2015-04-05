package game_engine.sprite.attributes;

import game_engine.PhysicsEngine;


public interface IMovement {

    public abstract void setVelocity (double vel);

    public abstract double getVelocity ();

    public abstract void setAcceleration (double accel);

    public abstract double getAcceleration ();

    public abstract void setTargetX (double x);

    public abstract void setTargetY (double y);

    public abstract double getTargetX ();

    public abstract double getTargetY ();

    /**
     * method definePhysics
     * @param physics the corresponding physics engine to set to the sprite
     */
    public abstract void definePhysics (PhysicsEngine physics);

    /**
     * method getPhysics
     * @return the PhysicsEngine of the current sprite
     */
    public abstract PhysicsEngine getPhysics ();

    public abstract void setX (double x);

    public abstract double getX ();

    public abstract void setY (double y);

    public abstract double getY ();

}
