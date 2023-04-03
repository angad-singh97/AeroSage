package io.koatech.aerosage.utils.preproc;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonDataFormat;

import java.io.File;
import java.util.Map;

public class OpenFlightsCamel {

    public static void main(String[] args) {

        try {
            // Create a CamelContext
            CamelContext context = new DefaultCamelContext();
            // Add routes to the context
            context.addRoutes(new OpenFlightsRouter());
            // Start the context
            context.start();
            // Wait for some time to let the routes complete
            Thread.sleep(10000);
            // Stop the context
            context.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    static class OpenFlightsRouter extends RouteBuilder {
        @Override
        public void configure() throws Exception {

            // Read the OpenFlights data from the DAT file using the CSV component
            String airportFilePath = "D:/project/AeroSage/AeroSage/datasources/openflights";
            String outputDir = "D:/project/AeroSage/AeroSage/datasources/output";
//        from("file:/path/to/openflights/dat/files?noop=true")
//                .unmarshal().csv()

            CsvDataFormat dataFormat = new CsvDataFormat();
            dataFormat.setDelimiter(',');

            File datFile = new File(airportFilePath);
            from("file:" + datFile.getAbsolutePath() + "?noop=true&include=.*\\.dat").unmarshal(dataFormat)
                    // more route configuration here


                    // Convert the data into JSON format using the Jackson component
                    .marshal().json()

                    // Write the JSON data to MongoDB
//                .to("mongodb:myDb?database=airportDb&collection=airports&operation=insert");
                    .process(exchange -> {
                        String inputFilePath = exchange.getIn().getHeader(Exchange.FILE_PATH, String.class);
                        String inputFileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
                        String outputFileName = inputFileName.replace(".dat", ".json");
                        String outputFilePath = outputDir + "/" + outputFileName;
                        exchange.getIn().setHeader(Exchange.FILE_NAME, outputFileName);
                        exchange.getIn().setHeader(Exchange.FILE_PATH, outputFilePath);
                    })
                    .to("file:"+outputDir);
        }
    }

}