import com.chilkatsoft.*;

public class ChilkatExample
{
      static {
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }

  public static void main(String argv[])
  {
    CkUpload upload = new CkUpload();

    //  Specify the page (ASP, ASP.NET, Perl, Python, Ruby, CGI, etc)
    //  that will process the HTTP Upload.
    upload.put_Hostname("www.chilkatsoft.com");
    upload.put_Path("/receiveUpload.aspx");

    //  Add one or more files to be uploaded.
    upload.AddFileReference("file1","dude.gif");
    upload.AddFileReference("file2","pigs.xml");
    upload.AddFileReference("file3","sample.doc");

    //  Do the upload.  The method returns when the upload
    //  is completed.
    //  This component also includes asynchronous upload capability,
    //  which is demonstrated in another example.
    boolean success;
    success = upload.BlockingUpload();
    if (success != true) {
        System.out.println(upload.lastErrorText());
    }
    else {
        System.out.println("Files uploaded!");
    }

  }
}