package src.model.constructs.items;

import java.util.UUID;

import src.Effect;
import src.FacingDirection;

public class SpreadingLineAreaEffectItem extends Item {
	
	int current_size_ = 0;
	int max_size_;
	FacingDirection direction_ = null;
	Effect effect_;
	boolean wait_turn_;
	
    public SpreadingLineAreaEffectItem(int max_size, Effect effect, FacingDirection direction) {
    	super(UUID.randomUUID().toString(), '?', false, true, false);
    	super.setViewable(false); // invisible
    	max_size_ = max_size;
    	effect_ = effect;
    	direction_ = direction;
    }
    
	@Override
    public void takeTurn() {
		wait_turn_ = !wait_turn_;
		if (wait_turn_) {
			for (int i = 0; i < 3; i++)
				this.getMapRelation().areaEffectFunctor.effectLine(current_size_, 1, effect_, direction_);
				++current_size_;
				if(current_size_ > max_size_) {
					this.getMapRelation().removeMyselfFromTheMapCompletely();
				}
		}
    }
}
