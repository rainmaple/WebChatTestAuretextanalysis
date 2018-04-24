package testAzure;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class Document {
    public String id, language, text;
    public Document(String id, String language, String text){
        this.id = id;
        this.language = language;
        this.text = text;
    }
}

class Documents {
    public List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<Document>();
    }
    public void add(String id, String language, String text) {
        this.documents.add (new Document (id, language, text));
    }
}

public class Process extends HttpServlet {
		private static final long serialVersionUID = 1L;
		static String accessKey = "你的apikey";
	    static String host = "https://westcentralus.api.cognitive.microsoft.com";
	    static String path = "/text/analytics/v2.0/keyPhrases";

	    public static String GetKeyPhrases (Documents documents) throws Exception {
	        String text = new Gson().toJson(documents);
	        byte[] encoded_text = text.getBytes("UTF-8");

	        URL url = new URL(host+path);
	        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "text/json");
	        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
	        connection.setDoOutput(true);

	        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	        wr.write(encoded_text, 0, encoded_text.length);
	        wr.flush();
	        wr.close();

	        StringBuilder response = new StringBuilder ();
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            response.append(line);
	        }
	        in.close();

	        return response.toString();
	    }

	    public static String prettify(String json_text) {
	        JsonParser parser = new JsonParser();
	        JsonObject json = parser.parse(json_text).getAsJsonObject();
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        return gson.toJson(json);
	    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
	public boolean isChinese(String strName) {
	    char[] ch = strName.toCharArray();
	    for (int i = 0; i < ch.length; i++) {
	        char c = ch[i];
	        if (isChinese(c)) {
	            return true;
	        }
	    }
	    return false;
	}
	 
	private boolean isChinese(char c) {
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
	            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	        return true;
	    }
	    return false;
	}
	public static String getEncoding(String str) {        
	       String encode = "GB2312";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s = encode;        
	              return s;        
	           }        
	       } catch (Exception exception) {        
	       }        
	       encode = "ISO-8859-1";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s1 = encode;        
	              return s1;        
	           }        
	       } catch (Exception exception1) {        
	       }        
	       encode = "UTF-8";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s2 = encode;        
	              return s2;        
	           }        
	       } catch (Exception exception2) {        
	       }        
	       encode = "GBK";        
	      try {        
	          if (str.equals(new String(str.getBytes(encode), encode))) {        
	               String s3 = encode;        
	              return s3;        
	           }        
	       } catch (Exception exception3) {        
	       }        
	      return "";        
	   }    

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		String json = request.getParameter("news");
		String language=null;
		if(isChinese(json)){
			language="zh_chs";
			System.out.println("此时的语言是"+language);
		}else{
			language="en";
			
		}
		System.out.println(json);
		PrintWriter out = response.getWriter();
		try {
            Documents documents = new Documents ();
            documents.add ("1", language, json);
           // documents.add ("2", "es", "Si usted quiere comunicarse con Carlos, usted debe de llamarlo a su telefono movil. Carlos es muy responsable, pero necesita recibir una notificacion si hay algun problema.");
            //documents.add ("3", "en", "The Grand Hotel is a new hotel in the center of Seattle. It earned 5 stars in my review, and has the classiest decor I've ever seen.");
            String wresponse = GetKeyPhrases (documents);
            JSONObject  myJson = JSONObject.fromObject(wresponse);
            JSONArray nn= myJson.getJSONArray("documents");
            JSONObject row = (JSONObject) nn.get(0); 
            JSONArray ja= (JSONArray) row.get("keyPhrases");
            response.setCharacterEncoding("UTF-8");    
            for (int i = 0; i < ja.size(); i++) {
                //提取出ja中的所有
                String str = (String) ja.get(i);
               // String encode=getEncoding(str);
               // System.out.println(encode+"***************");
                String str1 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
                System.out.println("关键词"+i+":"+str1);
                out.print("关键词"+i+":"+str1);
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
		out.flush();
		out.close();
	}
}
