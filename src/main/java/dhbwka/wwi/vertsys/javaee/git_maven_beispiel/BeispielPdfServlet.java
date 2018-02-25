/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.git_maven_beispiel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.toucanpdf.DocumentBuilder;
import org.toucanpdf.model.Font;
import org.toucanpdf.model.FontFamilyType;
import org.toucanpdf.model.ImageType;

/**
 * Servlet, das ein kleines PDF-Dokument erzeugt. Vgl. http://toucanpdf.org/
 */
@WebServlet(urlPatterns = {"/beispiel.pdf"})
public class BeispielPdfServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PDF generieren
        DocumentBuilder pdf = new DocumentBuilder();
        Font font = pdf.addFont().family(FontFamilyType.HELVETICA).bold();

        pdf.title("PDF-Servlet Beispiel")
                .writtenBy("Verteilte Systeme")
                .addText("Hallo Welt!").size(36).font(font);

        // Giraffe: https://pixabay.com/en/giraffe-animals-zoo-funny-fauna-901009/
        ServletContext context = this.getServletContext();
        InputStream imageData = context.getResourceAsStream("/WEB-INF/giraffe.jpg");
        pdf.addImage(imageData, ImageType.JPEG).width(350).on(20, 770);
        
        // Und an den Browser schicken
        OutputStream out = response.getOutputStream();

        response.setContentType("application/pdf");
        pdf.finish(out);
        out.flush();
    }
}
