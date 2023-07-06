package com.openwell.flashsale;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> demo = new CompletableFuture<>();
//        demo.complete("success");
//        CompletableFuture<String> demo = CompletableFuture.completedFuture(print());
// 异步 操作
//        CompletableFuture<String> test = CompletableFuture.supplyAsync(() -> {
//            System.out.println("看到客户 something by thread" + Thread.currentThread().getName());
//            return "success";
//        });

//       启动四个线程执行
//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        CompletableFuture<String> test = CompletableFuture.supplyAsync(() -> {
//            System.out.println("看到客户 something by thread" + Thread.currentThread().getName());
//            return "success";
//        }, executor);
    //可以没有返回方法
//        CompletableFuture<String> test = CompletableFuture.supplyAsync(() -> {
//            System.out.println("do something fist " + Thread.currentThread().getName());
//        }).get();

// 顺序执行
//       ExecutorService executorService = Executors.newFixedThreadPool(4);
//       CompletableFuture<String> stept1 = CompletableFuture.supplyAsync(()->{
//           System.out.println("执行【步骤1】");
//           return "【步骤1的执行结果】";
//
//       },executorService);
//
//        CompletableFuture<String> stept2 = stept1.thenApply(result ->{
//            System.out.println("上一步操作结果为：" + result);
//            return "【步骤2的执行结果】";
//        });
//
//        System.out.printf(stept2.get());
//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("执行【步骤1】");
//            return "【步骤1的执行结果】";
//        }, executor);
//
//        CompletableFuture<String> step2 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("执行【步骤2】");
//            return "【步骤2的执行结果】";
//        }, executor);
//
//        CompletableFuture<String> step3 = step1.thenCombine(step2, (result1, result2) -> {
//            System.out.println("前两步操作结果分别为：" + result1 + result2);
//            return "【步骤3的执行结果】";
//        });
//
//        System.out.println("步骤3的执行结果：" + step3.get());
//
//        executor.shutdown();

//        ExecutorService executor = Executors.newFixedThreadPool(4);

// 代码优化
//         CompletableFuture.anyOf(
//
//                 CompletableFuture.runAsync(()->print1("111"),executor),
//                 CompletableFuture.runAsync(()->print2("111"),executor)
//        )
//                .thenAccept(ignore-> print1("222"))
//                .thenAccept(ignore->print2("2222222"))
//                .join();

//        CompletableFuture<String> a = CompletableFuture.supplyAsync(() -> {
//            return "A";
//        });
//
//        CompletableFuture<String> b = a.thenApply(res -> {
//            return "B " + res;
//        });

        AtomicInteger seq = new AtomicInteger(0);
        Function<String, String> func = s -> s + " | " + seq.incrementAndGet();

        CompletableFuture<String> a = new CompletableFuture<>();
        CompletableFuture<String> b = a.thenApply(func);







    }

    private static void print1(String s ) {

        System.out.println("1 something by thread" + Thread.currentThread().getName());



    }

    private static void print2(String s) {

        System.out.println("2 something by thread" + Thread.currentThread().getName());



    }


}
