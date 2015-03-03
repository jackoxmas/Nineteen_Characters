/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view;

import src.IO_Bundle;


/**
 * Players see the MapView while they are interacting with the map
 *
 * @author Matthew B, Jessan, JohnReedLOL
 */

public final class MapView extends Viewport {
    
    // map_relationship_ is used in place of a map_referance_
    private transient char[][] view_contents_;


	@Override
	public void renderToDisplay(IO_Bundle bundle) {
		render(bundle);//All that's needed for now.
		
	}
	/*
	 * Constructor
	 */
	public MapView(){
		super();
		view_contents_ = getContents();		
	}
	/*
	 * Helper method to keep renderToDisplay pure
	 */
	private void render(IO_Bundle bundle){
		clear();
		makeSquare(0, 0,width_-1,height_-1);
		for(int i = 0;i<width_-2 && i < bundle.view_for_display_[0].length;++i){
			for(int j = 0;j<height_-2 && j< bundle.view_for_display_.length;++j){
				view_contents_[i+1][j+1] = bundle.view_for_display_[bundle.view_for_display_.length-j-1][i];
			}

		}
	}
	@Override
	public boolean getInput(char c) {
		// TODO Auto-generated method stub
		return false;
	}
}
