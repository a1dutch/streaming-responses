# StreamingResponseBody Bug

Toggle the relevant hibernate version in `build.gradle` line 20 or 21 then run `./gradlew bootRun`

You can then run the following curl which reproduces the error

```
curl 'http://localhost:8080/todos/report' -X POST -i
```


# Hibernate 6.5.3

200 response code is returned to client with a csv report

```
Content-Disposition: attachment; filename=report.csv
Content-Type: text/csv
Transfer-Encoding: chunked
Date: Thu, 23 Jan 2025 10:06:01 GMT

id,task
671b72e2-b1a4-4061-afa2-b0d054854cc3,task 0
2f129cac-c4b7-4721-a09c-4e1fb0277944,task 1
5bf392c6-8ed7-4377-8b3b-a4c1687f37eb,task 2
63a8b4b8-095d-4a2c-b1f8-09ce4b919653,task 3
bc3e2938-6673-4c22-acbb-1af7852e188d,task 4
...
```

# Hibernate 6.6.0

500 resposne code is returned to client and stack trace is below:

```
org.h2.jdbc.JdbcSQLNonTransientException: The object is already closed [90007-232]
	at org.h2.message.DbException.getJdbcSQLException(DbException.java:566) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.message.DbException.getJdbcSQLException(DbException.java:489) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.message.DbException.get(DbException.java:223) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.message.DbException.get(DbException.java:199) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.message.DbException.get(DbException.java:188) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.jdbc.JdbcResultSet.checkClosed(JdbcResultSet.java:3536) ~[h2-2.3.232.jar:2.3.232]
	at org.h2.jdbc.JdbcResultSet.next(JdbcResultSet.java:124) ~[h2-2.3.232.jar:2.3.232]
	at com.zaxxer.hikari.pool.HikariProxyResultSet.next(HikariProxyResultSet.java) ~[HikariCP-5.1.0.jar:na]
	at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.advanceNext(JdbcValuesResultSetImpl.java:270) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.processNext(JdbcValuesResultSetImpl.java:150) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.sql.results.jdbc.internal.AbstractJdbcValues.next(AbstractJdbcValues.java:19) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.sql.results.internal.RowProcessingStateStandardImpl.next(RowProcessingStateStandardImpl.java:99) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.internal.ScrollableResultsImpl.next(ScrollableResultsImpl.java:51) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.query.internal.ScrollableResultsIterator.hasNext(ScrollableResultsIterator.java:33) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:132) ~[na:na]
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1845) ~[na:na]
	at java.base/java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:762) ~[na:na]
	at com.example.demo.controller.TodoController$1.writeTo(TodoController.java:45) ~[main/:na]
	at org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler$StreamingResponseBodyTask.call(StreamingResponseBodyReturnValueHandler.java:110) ~[spring-webmvc-6.2.0.jar:6.2.0]
	at org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler$StreamingResponseBodyTask.call(StreamingResponseBodyReturnValueHandler.java:97) ~[spring-webmvc-6.2.0.jar:6.2.0]
	at org.springframework.web.context.request.async.WebAsyncManager.lambda$startCallableProcessing$4(WebAsyncManager.java:368) ~[spring-web-6.2.0.jar:6.2.0]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]
```
