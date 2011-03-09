using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace QUploadToRapidshare
{
    class QRapidshare
    {
        public string QUploadToRapidshare(string FilePath, string username, string password, int AccountType)
        {
            FileSystemInfo _file = new FileInfo(FilePath);
            DateTime dateTime2 = DateTime.Now;
            long l2 = dateTime2.Ticks;
            string s1 = "----------" + l2.ToString("x");
            System.Net.HttpWebRequest httpWebRequest = GetWebrequest(s1);
            using (System.IO.FileStream fileStream = new FileStream(_file.FullName, FileMode.Open, FileAccess.Read, FileShare.Read))
            {
                byte[] bArr1 = Encoding.ASCII.GetBytes("\r\n--" + s1 + "\r\n");
                string s2 = GetRequestMessage(s1, _file.Name, username, password, AccountType);
                byte[] bArr2 = Encoding.UTF8.GetBytes(s2);
                Stream memStream = new MemoryStream();
                memStream.Write(bArr1, 0, bArr1.Length);
                memStream.Write(bArr2, 0, bArr2.Length);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    memStream.Write(buffer, 0, bytesRead);

                }
                httpWebRequest.ContentLength = memStream.Length;
                fileStream.Close();

                Stream requestStream = httpWebRequest.GetRequestStream();

                memStream.Position = 0;
                byte[] tempBuffer = new byte[memStream.Length];
                memStream.Read(tempBuffer, 0, tempBuffer.Length);
                memStream.Close();
                requestStream.Write(tempBuffer, 0, tempBuffer.Length);
                requestStream.Close();






            }
            string tm = "";
            using (Stream stream = httpWebRequest.GetResponse().GetResponseStream())
            using (StreamReader streamReader = new StreamReader(stream))
            {
                tm = streamReader.ReadToEnd();

            }
            return tm;
        }
        private string GetRequestMessage(string boundary, string FName, string username, string password, int AccountType)
        {

            System.Text.StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.Append("--");
            stringBuilder.Append(boundary);
            stringBuilder.Append("\r\n");
            stringBuilder.Append("Content-Disposition: form-data; name=\"toolmode2\"");
            stringBuilder.Append("\r\n");
            stringBuilder.Append("\r\n");
            stringBuilder.Append("1");
            stringBuilder.Append("\r\n");
            stringBuilder.Append(boundary);
            stringBuilder.Append("\r\n");
            if (AccountType != 0)//Free User
            {
                if (AccountType == 1) //Premium Account
                {
                    stringBuilder.Append("Content-Disposition: form-data; name=\"login\"");
                }
                else //Collector Account
                {
                    stringBuilder.Append("Content-Disposition: form-data; name=\"freeaccountid\"");
                }
                stringBuilder.Append("\r\n");
                stringBuilder.Append("\r\n");
                stringBuilder.Append(username);
                stringBuilder.Append("\r\n");
                stringBuilder.Append(boundary);
                stringBuilder.Append("\r\n");
                stringBuilder.Append("Content-Disposition: form-data; name=\"password\"");
                stringBuilder.Append("\r\n");
                stringBuilder.Append("\r\n");
                stringBuilder.Append(password);
                stringBuilder.Append("\r\n");
            }

            stringBuilder.Append(boundary);
            stringBuilder.Append("\r\n");
            stringBuilder.Append("Content-Disposition: form-data; name=\"");
            stringBuilder.Append("filecontent");
            stringBuilder.Append("\"; filename=\"");
            stringBuilder.Append(FName);
            stringBuilder.Append("\"");
            stringBuilder.Append("\r\n");
            stringBuilder.Append("Content-Type: ");
            stringBuilder.Append("multipart/form-data");
            stringBuilder.Append("\r\n");
            stringBuilder.Append("Content-Transfer-Encoding: ");
            stringBuilder.Append("binary");
            stringBuilder.Append("\r\n");
            stringBuilder.Append("\r\n");
            return stringBuilder.ToString();
        }
        private CookieContainer _cockies = new CookieContainer();
        private HttpWebRequest GetWebrequest(string boundary)
        {
            WebClient wc = new WebClient();
            Uri url0 = new Uri("http://rapidshare.com/cgi-bin/rsapi.cgi?sub=nextuploadserver_v1");
            int uploadserver = int.Parse(wc.DownloadString(url0).Trim());
            //label2.Text = uploadserver.ToString();
            System.Uri uri = new Uri("http://rs" + uploadserver + "l3" + ".rapidshare.com/cgi-bin/upload.cgi");
            System.Net.HttpWebRequest httpWebRequest = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(uri);
            httpWebRequest.CookieContainer = _cockies;
            httpWebRequest.ContentType = "multipart/form-data; boundary=" + boundary;
            httpWebRequest.UserAgent = "RapidUploader[v1,2]";
            httpWebRequest.Referer = "http://rapidshare.com/";
            httpWebRequest.Method = "POST";
            httpWebRequest.KeepAlive = true;
            httpWebRequest.Timeout = -1;
            httpWebRequest.Headers.Add("Accept-Charset", "iSO-8859-1,utf-8;q=0.7,*;q=0.7");
            httpWebRequest.Headers.Add("Accept-Encoding", "identity");
            httpWebRequest.Headers.Add("Accept-Language", "de-de;q=0.5,en;q=0.3");
            return httpWebRequest;
        }
    }
}
