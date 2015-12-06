///*
//* To change this license header, choose License Headers in Project Properties.
//* To change this template file, choose Tools | Templates
//* and open the template in the editor.
//*/
//package General;
//
//import java.io.OutputStream;
//import java.net.URL;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.faces.context.ExternalContext;
//import javax.faces.context.FacesContext;
//import javax.faces.view.ViewScoped;
//import javax.inject.Named;
//import javax.servlet.http.HttpServletResponse;
//import org.docx4j.org.xhtmlrenderer.pdf.ITextRenderer;
//
//@Named("createPDF")
//@ViewScoped
//public class createPDF {
//    
//    public void createPDF() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        String port = String.valueOf(externalContext.getRequestServerPort());
//        String appname = externalContext.getRequestContextPath();
//        String protocol = externalContext.getRequestScheme();
//        String PDF_PAGE = "GraficarEncuestas";
//        String servername = "localhost";
//        String url = protocol + "://" + servername + ":" + port + appname + PDF_PAGE;
//        try {
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocument(new URL(url).toString());
//            renderer.layout();
//            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//            response.reset();
//            response.setContentType("application/pdf");
//            String PDF_FILE_NAME = "Estadistica Encuestas";
//            response.setHeader("Content-Disposition", "inline; filename=\"" +PDF_FILE_NAME+ "\"");
//            OutputStream browserStream = response.getOutputStream();
//            renderer.createPDF(browserStream);
//        } catch (Exception ex) {
//            Logger.getLogger(createPDF.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}