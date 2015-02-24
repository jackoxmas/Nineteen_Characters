/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view;

import src.model.map.constructs.MapViewable;

/**
 * Players see the MapView while they are interacting with the map
 *
 * @author Matthew B, Jessan, JohnReedLOL
 */

public final class MapView extends Viewport {
    
    // map_relationship_ is used in place of a map_referance_
    private transient char[][] view_contents_;
    private int x_;
    private int y_;//Set these to center via avatar later.
    private MapViewable map_;

	@Override
	public void renderToDisplay() {
		render();//All that's needed for now.
		
	}
	/*
	 * Constructor, inits center to (0,0)
	 */
	public MapView(MapViewable _map){
		super();
		view_contents_ = getContents();
		x_=0;
		y_=0;
		map_ = _map;
	}
	/*
	 * Set the center to render the view from
	 * @param int x, int y, x and y coords
	 */
	public void setCenter(int x, int y){
		x_ = x;
		y_= y;
	}
	/*
	 * Helper method to keep renderToDisplay pure
	 */
	private void render(){
		clear();
		makeSquare(0, 0,width_-1,height_-1);
		for(int i = 1;i!=width_-1;++i){
			for(int j = 1;j!=height_-1;++j){
				view_contents_[i][j] = map_.getTileRepresentation(i-width_/2+x_,height_/2-j+y_);
			}

		}
	}
	@Override
	public boolean getInput(char c) {
		// TODO Auto-generated method stub
		return false;
	}
}
