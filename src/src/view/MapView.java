/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import src.controller.Avatar;

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

	@Override
	public void renderToDisplay() {
		render();//All that's needed for now.
		
	}
	public MapView(){
		super();
		view_contents_ = getContents();
		x_=0;
		y_=0;
	}
	public void setCenter(int x, int y){
		x_ = x;
		y_= y;
	}
	private void render(){
		clear();
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
