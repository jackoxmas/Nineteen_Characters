package src.io.view;

import java.awt.Color;

import src.IO_Bundle;
import src.model.Vector2;

public class MapEditorView extends MapView {

	public MapEditorView() {
		// TODO Auto-generated constructor stub
	}
	protected void renderToDisplayInternally(IO_Bundle bundle) {
		super.renderToDisplayInternally(bundle);
		drawCross();
	}

	protected void drawCross(){
		int mid_x = this.getWidth()/2+1;
		int mid_y = this.getHeight()/2+1;
		for(int i = 0; i!=this.getHeight(); ++i){
			if(i!=mid_y){
				this.getCharContents()[mid_x][i] = '|';
				this.getColorContents()[mid_x][i] = Color.black;
			}
		}
		for(int i = 0; i!=this.getWidth(); ++i){
			if(i!=mid_x){
				this.getCharContents()[i][mid_y] = '-';
				this.getColorContents()[i][mid_y] = Color.black;
			}
		}
		
	}

}
