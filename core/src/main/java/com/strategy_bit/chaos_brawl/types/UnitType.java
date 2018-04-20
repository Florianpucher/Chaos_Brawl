package com.strategy_bit.chaos_brawl.types;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public enum UnitType {
    RANGED {
        @Override
        public double getCosts() {
            return 10.0;
        }
    }, MELEE {
        @Override
        public double getCosts() {
            return 10.0;
        }
    }, MAINBUILDING {
        @Override
        public double getCosts() {
            return 0;
        }
    }, TOWER {
        @Override
        public double getCosts() {
            return 0;
        }
    };



    public abstract double getCosts();
}
