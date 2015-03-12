package src.io.view;

import java.util.ArrayList;

import src.IO_Bundle;
/**
 * View for the chatbox. Separate from the other views.
 * @author mbregg
 *
 */
public class ChatBoxViewPort {
	private ArrayList<String> view_content_strings_ = new ArrayList<String>();
	private ArrayList<String> headings_ = new ArrayList<String>();
	private ArrayList<String> result_ = new ArrayList<String>();
	private int whiteSpaceCount_ = 1;
	public ChatBoxViewPort() {
	}
	public void renderToDisplay(IO_Bundle bundle){
		result_.clear();
		if(bundle == null){result_.add("GAME OVER YOU ARE DEAD"); return;}//If null we are dead, so simply say that and do nothing else
		if(bundle.strings_for_communication_ == null){return;}
		clear();
		for(int i = 0; i!=bundle.strings_for_communication_ .size();++i){
			String temp = String.valueOf(i) + ": ";
			view_content_strings_.add(bundle.strings_for_communication_.get(i));
			headings_.add(temp);
		}
		result_ = new ArrayList<String>(headings_.size());
		addWhiteSpace(result_);
		for(int i = 0;i!=headings_.size();++i){
			result_.add(headings_.get(i)+view_content_strings_.get(i));
		}
		addWhiteSpace(result_);

	}
	private void addWhiteSpace(ArrayList<String> list){
		for(int i = 0; i!=whiteSpaceCount_;++i){list.add(System.lineSeparator());}
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

		return result_;
	}

}
