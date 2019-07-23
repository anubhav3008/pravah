package com.pravah.web.service;

import com.pravah.web.dao.MeetingJsonDao;
import com.pravah.web.utils.Base64ImageProvider;
import com.pravah.web.utils.ImageHelper;
import com.pravah.web.utils.VelocityUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgendaService {
    @Autowired
    MeetingJsonDao meetingJsonDao;
    @Autowired
    VelocityUtil velocityUtil;

    @Autowired
    ImageHelper imageHelper;
    ObjectMapper mapper = new ObjectMapper();

    public byte[] getAgenda(int meetingId) throws Exception {
        JsonNode meeting = meetingJsonDao.getMeetingJson(meetingId);

        Map<String, String> images= new HashMap<>();
        images.put("logo",imageHelper.getBaseImageBase64(ImageHelper.toastmastersLogoUrl));
        String str=velocityUtil.transform((ObjectNode) mapper.readTree(mapper.writeValueAsString(meeting)),"template/agenda_pdf.vm", images);
        System.out.println("html="+str );
        Document document = new Document(PageSize.LEGAL, 10, 10,10, 10);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("agenda.pdf"));

        document.open();

        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(str.getBytes()));

        document.close();

        RandomAccessFile f = new RandomAccessFile("agenda.pdf", "r");
        byte[] b = new byte[(int)f.length()];
        f.readFully(b);
        return b;
    }
}
