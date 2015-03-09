/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Color;
import java.util.ArrayList;

import src.model.map.constructs.EntityStatsPack;
import src.model.map.constructs.Occupation;
import src.model.map.constructs.PickupableItem;
import src.model.map.constructs.PrimaryHandHoldable;
import src.model.map.constructs.SecondaryHandHoldable;

/**
 * Contains contents of data for IO to user.
 *
 * @author JohnReedLOL
 */
public class IO_Bundle {

    public IO_Bundle(char[][] v, Color[][] c, ArrayList<PickupableItem> i,
            EntityStatsPack s, Occupation o, int n, int bi, int ba, int ob,
            PrimaryHandHoldable pri, SecondaryHandHoldable sec,
            ArrayList<String> sfc
    ) {
        view_for_display_ = v;
        color_for_display_ = c;
        inventory_ = i;
        stats_for_display_ = s;
        occupation_ = o;
        num_skillpoints_ = n;
        bind_wounds_ = bi;
        bargain_ = ba;
        observation_ = ob;
        primary_ = pri;
        second_ = sec;
        strings_for_communication_ = sfc;
    }
    public final char[][] view_for_display_;
    public final Color[][] color_for_display_;
    public final PrimaryHandHoldable primary_;
    public final SecondaryHandHoldable second_;
    public final ArrayList<PickupableItem> inventory_;
    public final EntityStatsPack stats_for_display_;
    public final Occupation occupation_;
    public final int num_skillpoints_;
    public final int bind_wounds_;
    public final int bargain_;
    public final int observation_;
    public final ArrayList<String> strings_for_communication_;

    public EntityStatsPack getStatsPack() {
        return stats_for_display_;
    }

    public Occupation getOccupation() {
        return occupation_;
    }
    public ArrayList<PickupableItem> getInventory(){
    	return inventory_;
    }
    public ArrayList<String> getSkillNames(){
    	ArrayList<String> result = new ArrayList<String>();
        result.add(occupation_.getSkillNameFromNumber(1));
        result.add(occupation_.getSkillNameFromNumber(2));
        result.add(occupation_.getSkillNameFromNumber(3));
        result.add(occupation_.getSkillNameFromNumber(4));
    	result.add(0,"bind_wounds_");
    	result.add(0,"bargain_");
    	result.add(0,"observation_");
    	return result;
    }
    public ArrayList<Integer> getSkillLevels(){
    	ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(occupation_.getSkill_1_());
        result.add(occupation_.getSkill_2_());
        result.add(occupation_.getSkill_3_());
        result.add(occupation_.getSkill_4_());
    	result.add(0,bind_wounds_);
    	result.add(0,bargain_);
    	result.add(0,observation_);
    	return result;
    	
    }
}