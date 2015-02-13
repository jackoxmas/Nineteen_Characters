/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;

import java.util.ArrayList;

import src.controller.Avatar;
import src.controller.Entity;
/**
 * Players see the AvatarCreationView when they chose their occupation.
 * @author Matthew B, Jessan, JohnReedLOL
 */
final class AvatarCreationView extends Viewport
{
    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("CCView", 35);
	

    private final Avatar avatar_reference_;
    private char[][] view_contents_;

    private ArrayList<String> title_;
    private ArrayList<String> smasherArt_;
    private ArrayList<String> sneakerArt_;
    private ArrayList<String> summonerArt_;
    private String sneakerString_ = "C to Select a Sneaker:";
    private String summonerString_ = "W to Select a Summoner:";
    private String smasherString_ = "S to Select a Smasher:";
    private void renderArray(){
    	for(int i = 0; i!=title_.size();++i){
    		writeStringToContents(5,1+i,title_.get(i));
    	}

    	int heightFromBottom = height_-sneakerArt_.size()-1;
    	for(int i = 0; i!=sneakerArt_.size();++i){
    		int offCenteredLength = length_/2-sneakerArt_.get(0).length()/2;
    		writeStringToContents(offCenteredLength,heightFromBottom+i,sneakerArt_.get(i));
    		//Above is a bit long, but I don't really think it's worth moving to a function, doing so would be just as cluttering. 
    	}
    	
    	writeStringToContents(1,heightFromBottom+1,sneakerString_);
    	heightFromBottom-=smasherArt_.size();
    	for(int i = 0; i!=smasherArt_.size();++i){
    	 writeStringToContents(1,heightFromBottom+i,smasherArt_.get(i));
    	}
    	heightFromBottom--;
    	writeStringToContents(1,heightFromBottom,smasherString_);
    	
    	//Summoner positioning a bit weird
    	heightFromBottom+=summonerArt_.size()-3;//3 is the magic constant. Really, it makes it looks properly spaced.
    	int midpoint = length_/2;
    	writeStringToContents(midpoint,heightFromBottom,summonerString_);
    	++heightFromBottom;
    	for(int i = 0; i!=summonerArt_.size();++i){
    		writeStringToContents(midpoint,heightFromBottom+i,summonerArt_.get(i));
    	}


    }

    /*
     * Generates a new ChaAvatarCreationViewses avatar_reference_ to modify the avatar.
     */
    public AvatarCreationView(Avatar my_avatar) {
    	avatar_reference_ = my_avatar;

    	view_contents_=new char[length_][height_];
    	makeSquare(0,0,length_-1,height_-1);//This is a static view, no need to dynamically render it each turn.
		title_ = getAsciiArtFromFile("src/view/ASCIIART/class.txt");
		smasherArt_ = getAsciiArtFromFile("src/view/ASCIIART/smasher.txt");
		sneakerArt_ = getAsciiArtFromFile("src/view/ASCIIART/sneaker.txt");
		summonerArt_ = getAsciiArtFromFile("src/view/ASCIIART/summoner.txt");
		renderArray();

    	makeSquare(0,0,length_-1,height_-1);//This is a static view, no need to dynamically render it each turn.

    	view_contents_=new char[length_][height_];
    	makeSquare(0,0,length_-1,height_-1);//This is a static view, no need to dynamically render it each turn.

		title_ = getAsciiArtFromFile("src/view/ASCIIART/class.txt");
		smasherArt_ = getAsciiArtFromFile("src/view/ASCIIART/smasher.txt");
		sneakerArt_ = getAsciiArtFromFile("src/view/ASCIIART/sneaker.txt");
		summonerArt_ = getAsciiArtFromFile("src/view/ASCIIART/summoner.txt");
		renderArray();


    	view_contents_=new char[length_][height_];



    	makeSquare(0,0,length_-1,height_-1);//This is a static view, no need to dynamically render it each turn.

    	view_contents_=new char[length_][height_];
    	makeSquare(0,0,length_-1,height_-1);//This is a static view, no need to dynamically render it each turn.

		title_ = getAsciiArtFromFile("src/view/ASCIIART/class.txt");
		smasherArt_ = getAsciiArtFromFile("src/view/ASCIIART/smasher.txt");
		sneakerArt_ = getAsciiArtFromFile("src/view/ASCIIART/sneaker.txt");
		summonerArt_ = getAsciiArtFromFile("src/view/ASCIIART/summoner.txt");
		renderArray();

    }
	@Override
	public void renderToDisplay() {
		// TODO Auto-generated method stub
		
	}
}
