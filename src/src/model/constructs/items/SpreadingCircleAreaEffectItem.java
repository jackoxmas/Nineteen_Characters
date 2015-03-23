package src.model.constructs.items;

import java.util.UUID;

import src.Effect;
import src.FacingDirection;

public class SpreadingCircleAreaEffectItem extends Item {

    public int getID() { return 20; }
	
	int current_size_ = 0;
	int max_size_;
	Effect effect_ = null;
	boolean wait_turn_;
	
    public SpreadingCircleAreaEffectItem(int max_size, Effect effect) {
    	super(UUID.randomUUID().toString(), '?', false, true, false);
    	super.setViewable(false); // invisible
    	max_size_ = max_size;
    	effect_ = effect;
    }
    
	@Override
    public void takeTurn() {
		wait_turn_ = !wait_turn_;
		if (wait_turn_) {
			this.getMapRelation().areaEffectFunctor.effectPerimeter(current_size_, 1, effect_);
			++current_size_;
			if(current_size_ > max_size_) {
				this.getMapRelation().removeMyselfFromTheMapCompletely();
			}
		}
    }
}
