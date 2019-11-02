package com.example.homecctv;

import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;


import android.os.AsyncTask;
import android.util.Log;

public class AndroidUploader {
    static String serviceDomain = "http:///35.221.206.41:52274";
    //    static String serviceDomain = "http:///35.221.206.41:52274";
    static String postUrl = serviceDomain + "/imageWrite";
    static String CRLF = "\r\n";
    static String twoHyphens = "--";
    static String boundary = "*****b*o*u*n*d*a*r*y*****";
    private String pictureFileName = null;
    private DataOutputStream dataStream = null;
    public String Name="/";
    public static void setServiceDomain(String domainName)       {
        serviceDomain = domainName;
    }

    public static String getServiceDomain()       {
        return serviceDomain;
    }

    public String uploadPicture(String pictureFileName,String id,String name)      {
        String[] temp = pictureFileName.split("/");
        int temp2=temp.length-1;
//        String temp_FileName=id+"_"+temp[temp2];
        String realFileName=id+"_"+name+".jpg";
        for(int i = 0; i<temp2; i++){
            Name=Name+temp[i]+"/";
        }
        Name=Name+realFileName;
        this.pictureFileName = pictureFileName;
        File uploadFile = new File(pictureFileName);

        if (uploadFile.exists())
            try    {
                FileInputStream fileInputStream = new FileInputStream(uploadFile);
                URL connectURL = new URL(postUrl);
                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundary+";charset=utf-8");
//                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.connect();
                dataStream = new DataOutputStream(conn.getOutputStream());
                writeFileField("img",Name, "image/jpg.", fileInputStream);
                dataStream.writeBytes(twoHyphens + boundary + twoHyphens + CRLF);

                fileInputStream.close();
                dataStream.flush();
                dataStream.close();
                dataStream = null;

                String response = getResponse(conn);
                int responseCode = conn.getResponseCode();
                if (response.contains("uploaded successfully"))
                    return "201";
                else
                    return "401";
            }
            catch (MalformedURLException mue) {
                return "400";
            }

            catch (IOException ioe) {
                String a = "error: " + ioe.getMessage();
                return a;
            }

            catch (Exception e) {
                return "unknown";

            }    else    {
            return "noPicture";
        }
    }
    private String getResponse(HttpURLConnection conn)       {
        try{
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            byte[] data = new byte[1024];
            int len = dis.read(data, 0, 1024);
            dis.close();
            int responseCode = conn.getResponseCode();
            if (len > 0)
                return new String(data, 0, len);
            else
                return "";
        }

        catch(Exception e)     {
            return "";
        }
    }
    private String getResponseOrig(HttpURLConnection conn)       {
        InputStream is = null;
        try   {
            is = conn.getInputStream();
            int ch;
            StringBuffer sb = new StringBuffer();
            while( ( ch = is.read() ) != -1 ) {
                sb.append( (char)ch );
            }
            return sb.toString();   // TODO Auto-generated method stub
        }
        catch(Exception e)   {
        }
        finally   {
            try {
                if (is != null)
                    is.close();
            } catch (Exception e) {}
        }
        return "";
    }

    private void writeFormField(String fieldName, String fieldValue)  {
        try  {
            dataStream.writeBytes(twoHyphens + boundary + CRLF);
            dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);
            dataStream.writeBytes(CRLF);
            dataStream.writeBytes(fieldValue);
            dataStream.writeBytes(CRLF);

        }    catch(Exception e) {
        }

    }

    private void writeFileField(
            String fieldName,
            String fieldValue,
            String type,
            FileInputStream fis)  {
        try {
            dataStream.writeBytes(twoHyphens + boundary + CRLF);
            dataStream.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\";filename=\"");
            dataStream.writeUTF(fieldValue);
            dataStream.writeBytes("\"" + CRLF);
            dataStream.writeBytes("Content-Type: " + type +  CRLF);
            dataStream.writeBytes(CRLF);
            int bytesAvailable = fis.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = fis.read(buffer, 0, bufferSize);
            while (bytesRead > 0)   {
                dataStream.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }
            dataStream.writeBytes(CRLF);
        }

        catch(Exception e)  {
        }

    }

    public static void main(String[] args)  {

//        if (args.length >= 0)  {
//            AndroidUploader gpu = new AndroidUploader();
//            String picName = args[0];
//            String b = gpu.uploadPicture(picName);
//        }
    }

}
