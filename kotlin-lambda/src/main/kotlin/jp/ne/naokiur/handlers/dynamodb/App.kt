package jp.ne.naokiur.handlers.dynamodb

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import jp.ne.naokiur.handlers.GatewayResponse
import java.util.*

/**
 * Handler for requests to Lambda function.
 */
class App : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any, context: Context): Any {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"

        val dynamodb = DynamoDBManager()
        val response = dynamodb.putItem("a", Calendar.getInstance())


        return GatewayResponse("result: $response", headers, 200)
    }
}
