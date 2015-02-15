/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import src.controller.Avatar;
import src.model.MapDisplay_Relation;

/**
 * Players see the MapView while they are interacting with the map
 *
 * @author Matthew B, Jessan, JohnReedLOL
 */

public final class MapView extends Viewport {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("MapView", 35);
    
    // map_relationship_ is used in place of a map_referance_
    private char[][] view_contents_;
    private int x_;
    private int y_;//Set these to center via avatar later.
    private Avatar AvatarRef_;

	@Override
	public void renderToDisplay() {
		render();//All that's needed for now.
		
	}
	public MapView(Avatar _avatar){
		super();
		AvatarRef_ = _avatar;
		view_contents_ = getContents();
	}
	private void render(){
		clear();
		x_=AvatarRef_.getMapRelation().getMyXCordinate();
		y_=AvatarRef_.getMapRelation().getMyYCordinate();
		System.out.println(x_ + " " + y_);
		makeSquare(0, 0,width_-1,height_-1);
		int midpointx_ = x_/2;
		int midpointy_ = y_/2;
		for(int i = 1;i!=width_-1;++i){
			for(int j = 1;j!=height_-1;++j){
				view_contents_[i][j] = map_relationship_.getTileRepresentation(i-width_/2+x_,height_/2-j+y_);
			}

		}
	}
	@Override
	public boolean getInput(char c) {
		// TODO Auto-generated method stub
		return false;
	}
}
