
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document doc;
		try {
			doc = Jsoup.connect("http://en.wikipedia.org/wiki/Main_Page").get();//gets the page source
			Elements newsHeadlines = doc.select("#mp-itn li");//# indicates the id and li indicates the tag, gets the elements between the two tags
			//Element firstchild = first.child(0);//gets the first element inside
			//String html = first.html();//gets the html code within the element
			//String texts = first.ownText();//gets the text within only the element
			System.out.println("News Headlines: ");
			for (int i = 0;i<newsHeadlines.size();i++)
			{
				Element news = newsHeadlines.get(i);
				String text = news.text();
				System.out.println("- " + text);
			}
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
