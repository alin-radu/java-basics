package duke.choice;

import io.helidon.common.http.Http;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

public class ItemList implements Handler {

    private Clothing[] items;

    public ItemList() {
    }

    public ItemList(Clothing[] items) {
        this.items = items;
    }

    @Override
    public void accept(ServerRequest serverRequest, ServerResponse serverResponse) {
        StringBuilder result = new StringBuilder();

        for (Clothing item : items) {
            result
                    .append("- ")
                    .append(item.toString())
                    .append('\n');
        }
        serverResponse.status(Http.Status.OK_200);
        serverResponse.headers().put("Content-Type", "text/plain; charset=utf-8");
        serverResponse.send(result);

    }
}
