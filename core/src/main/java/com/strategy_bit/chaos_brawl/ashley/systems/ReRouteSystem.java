package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.World;

public class ReRouteSystem extends IteratingSystem {
    private World world;
    private ComponentMapper<MovementComponent> mMovementComponent;
    private ComponentMapper<CombatComponent> mCombatComponent;

    public ReRouteSystem(World world) {
        super(Family.all(CombatComponent.class, MovementComponent.class).get());
        mMovementComponent = ComponentMapper.getFor(MovementComponent.class);
        mCombatComponent = ComponentMapper.getFor(CombatComponent.class);
        this.world=world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CombatComponent combatComponent = mCombatComponent.get(entity);
        MovementComponent movementComponent=mMovementComponent.get(entity);
        if (combatComponent != null && !combatComponent.isAttacking()&&combatComponent.isGetsAttacked()) {
            Array<Vector2> newPath;
            if (movementComponent.getPath().size>0){
                newPath=world.getPath(combatComponent.getAttacker(),movementComponent.getPath().get(0));
            }
            else {
                newPath=new Array<>();
            }
            movementComponent.setPath(newPath);
            movementComponent.getPath().addLast(combatComponent.getAttacker());
            combatComponent.setGetsAttacked(false);
        }
    }
}
