package com.wbo.TwitterManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbo.mavenproject1;

import io.reactivex.Maybe;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author aebs
 */
public class AppTest {

    //    @Test
//    public void whenSetFollowRedirects_thenNotRedirected()
//            throws IOException {
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .followRedirects(false)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://mobile.fransabank.dz/api/Releves/3402010000?page=1&size=10")
//                .build();
//
//        Call call = client.newCall(request);
//        Response response = call.execute();
//        System.out.println(response.body().string());
//
//        String list = Unirest.get("https://mobile.fransabank.dz/api/Releves/3402010000?page=1&size=10").asString().getBody();
//        System.out.println(list);
////assertThat(response.body().string(), equalTo(301));
//    }
    @Test
    public void whenSetFollowRedirects_thenNotRedirected()
            throws IOException {
        List<Todo> list = Arrays.asList(new Todo("12582"), new Todo("212"), new Todo("1"), new Todo("7878"));
        List<Todo1> list2 = Arrays.asList(new Todo1("11111"), new Todo1("212"), new Todo1("1"), new Todo1("7878"));

        Maybe<List<Todo>> a = Maybe.create(emitter -> {
            try {
                List<Todo> todos = list;
                if (todos != null && !todos.isEmpty()) {
                    emitter.onSuccess(todos);
                } else {
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        Maybe<List<Todo1>> b = Maybe.create(emitter -> {
            try {
                List<Todo1> todos = list2;
                if (todos != null && !todos.isEmpty()) {
                    emitter.onSuccess(todos);
                } else {
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        Maybe<List<Todo1>> res = Maybe.zip(a, b, (x, y) -> {
            String output = x.get(0).getName() + y.get(0).getName();
            //  System.out.println(output);
            return y;
        }).doOnSuccess(output -> {
            // System.out.println(output);
        });
        res.subscribe(
                item -> {
                    System.out.println(item.get(0).getName());
                },
                ex -> System.err.println("3333333333")
        );
    }

//    @Test
//    public void maybeZipEmptyTest() throws Exception {
//        Maybe<Optional<Integer>> a = Maybe.just(Optional.of(1));
//        Maybe<Optional<Integer>> b = Maybe.just(Optional.of(2));
//        Maybe<Optional<Integer>> empty = Maybe.just(Optional.empty());
//
//        TestObserver<String> observer = Maybe.zip(a, b, (x, y) -> {
//            String output = "test: a " + toStringOrEmpty(x) + " b " + toStringOrEmpty(y);
//            return output;
//        }).doOnSuccess(output -> {
//            System.out.println(output);
//        }).test();
//
//        observer.assertNoErrors();
//    }
//
//    private String toStringOrEmpty(Optional<Integer> value) {
//        if (value.isPresent()) {
//            return value.get().toString();
//        } else {
//            return "";
//        }
//    }
}
