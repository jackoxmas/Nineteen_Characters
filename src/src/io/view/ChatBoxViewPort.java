package src.io.view;

import java.util.ArrayList;

import src.IO_Bundle;

public class ChatBoxViewPort {
private ArrayList<String> view_content_strings_ = new ArrayList<String>();
private ArrayList<String> headings_ = new ArrayList<String>();
	public ChatBoxViewPort() {
	}
	public void renderToDisplay(IO_Bundle bundle){
		if(bundle.strings_for_communication_ == null){return;}
		clear();
		for(int i = 0; i!=bundle.strings_for_communication_ .size();++i){
			String temp = "";
			if(i != 0){temp = String.valueOf(i) + ": ";}
			view_content_strings_.add(bundle.strings_for_communication_.get(i));
			headings_.add(temp);
		}
		
	}
	private void clear(){
		view_content_strings_.clear();
		headings_.clear();
	}
	public String getChoice(int i){
		if(i < 0){return "";}
		if(i < view_content_strings_.size()){
			return view_content_strings_.get(i);
		}
		return "";
	}
	public ArrayList<String> getContents(){
		ArrayList<String> result = new ArrayList<String>(headings_.size());
		for(int i = 0;i!=headings_.size();++i){
			result.set(i, headings_.get(i)+view_content_strings_.get(i));
		}
		return result;
	}

}
