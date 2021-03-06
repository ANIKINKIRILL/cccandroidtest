package com.anikinkirill.cccandroidtest

import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import java.util.concurrent.TimeUnit

class Constants {

    companion object {

        const val personId = "85a57f85-a52d-4137-a0d1-62e61362f716"
        const val estimateId = "c574b0b4-bdef-4b92-a8f0-608a86ccf5ab"
        val errorPerson = Person("-1", "Error...", "Error...", "Error...", "Error...")
        val errorEstimate = Estimate(
            "-1",
            "Error...",
            "Error...",
            -1,
            -1,
            "Error...",
            "Error...",
            "Error...",
            "Error..."
        )

        const val INSERT_SUCCESS = "Insert success"
        const val INSERT_FAILURE = "Insert failure"

        const val timeDelay = 2L
        val timeUnit = TimeUnit.SECONDS

        const val json = "{" +
                " \"estimate\": {\n" +
                "    \"id\": \"c574b0b4-bdef-4b92-a8f0-608a86ccf5ab\",\n" +
                "    \"company\": \"Placebo Secondary School\",\n" +
                "    \"address\": \"32 Commissioners Road East\",\n" +
                "    \"number\": 32,\n" +
                "    \"revision_number\": 3,\n" +
                "    \"created_date\": \"2020-08-22 15:23:54\",\n" +
                "    \"created_by\": \"85a57f85-a52d-4137-a0d1-62e61362f716\",\n" +
                "    \"requested_by\": \"85a57f85-a52d-4137-a0d1-62e61362f716\",\n" +
                "    \"contact\": \"85a57f85-a52d-4137-a0d1-62e61362f716\"\n" +
                "  },\n" +
                "  \"person\": {\n" +
                "    \"id\": \"85a57f85-a52d-4137-a0d1-62e61362f716\",\n" +
                "    \"first_name\": \"Joseph\",\n" +
                "    \"last_name\": \"Sham\",\n" +
                "    \"email\": \"joseph.sham@fake.fake\",\n" +
                "    \"phone_number\": \"123-456-7890\"\n" +
                "  } " +
                "}"
    }

}