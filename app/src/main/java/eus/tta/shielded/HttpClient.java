package eus.tta.shielded;

import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ainhoa on 02/01/2016.
 */
public class HttpClient {
    private final static String AUTH = "Authorization";
    private final String baseURL;
    private final Map<String,String> properties = new HashMap<>();

    public HttpClient (String baseURL){ this.baseURL = baseURL; }

    public void setHttpBasicAuth(String user,String passwd){
        String sumUP = user+":"+passwd;
        String basicAuth = Base64.encodeToString(sumUP.getBytes(),Base64.DEFAULT);
        setAuthorization(String.format("Basic %s",basicAuth));
    }

    public String getAuthorization(){ return properties.get(AUTH); }

    public void setAuthorization (String auth) { properties.put(AUTH,auth); }

    public void setProperty (String name, String value){ properties.put(name,value); }

    private HttpURLConnection getConnection (String path) throws IOException{
        URL url  = new URL(String.format("%s/%s",baseURL,path));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        for(Map.Entry<String,String> property : properties.entrySet())
            conn.setRequestProperty(property.getKey(),property.getValue());
        return conn;
    }

    public String getString(String path) throws IOException {
        HttpURLConnection conn = null;
        try{
            conn = getConnection(path);
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                return br.readLine();
            }catch (Exception e){
                System.out.print(e.getMessage());
            }
        }finally {
            if(conn != null)
                conn.disconnect();
        }
        return "";
    }

    public JSONObject getJson(String path) throws IOException, JSONException{ return new JSONObject(getString(path));}

    public int postFile (String path,InputStream is, String fileName)throws IOException {
        String boundary = Long.toString(System.currentTimeMillis());
        String newLine = "\r\n";
        String prefix = "--";
        HttpURLConnection conn = null;
        try{
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            /*Para establecer la Cabecera*/
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            /*Prefijo*/
            out.writeBytes(prefix+boundary+newLine);
            /*Cabeceras para el fichero*/
            out.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""+fileName+"\""+newLine);
            out.writeBytes(newLine);
            /*Comienza el fichero*/
            byte[] data = new byte[1024*1024];
            int len;
            while((len = is.read(data)) > 0)
                out.write(data,0,len);
            /*fin dle fichero*/
            out.writeBytes(newLine);
            /*fin del mensaje*/
            out.writeBytes(prefix + boundary + prefix + newLine);
            out.close();
            return conn.getResponseCode();
        }finally {
            if(conn != null)
                conn.disconnect();
        }
    }
    }
