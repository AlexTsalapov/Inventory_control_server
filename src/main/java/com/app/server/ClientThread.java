package com.app.server;


import com.app.model.Category;
import com.app.model.Product;
import com.app.model.Storage;
import com.app.model.User;
import com.app.service.CategoryService;
import com.app.service.ProductService;
import com.app.service.StorageService;
import com.app.service.UserService;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ClientThread extends Thread {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private Request request;


    private UserService userService = new UserService();
    private StorageService storageService = new StorageService();
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();


    public ClientThread(Socket clientSocket) {
        try {
            request = new Request();
            this.clientSocket = clientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Не удалось создать поток для клиента");
        }
    }

    private void send(Request request) throws IOException {//отправка клиенту информации
        String data = (new JSON<Request>().toJson(request));
        out.writeUTF(data);
        Scanner scanner=new Scanner(System.in);
        scanner.next
    }

    public Request get() throws IOException {//получение иннформации от клиента
        String data = in.readUTF();
        return (new JSON<Request>()).fromJson(data, Request.class);
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {//соединение с клиентом
                request = get();//получение отправленой информации
                Request.RequestType requestType = request.getRequestType();//получения типа выполняемой операции
                switch (requestType) {
                    case Registration: {
                        try {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": регистрация пользователя");
                            User user = (new JSON<User>()).fromJson(request.getRequestMessage(), User.class);
                            request.setRequestMessage(userService.add(user));
                            if (request.getRequestMessage().equals("true")) {
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": пользователь зарегистрирован");
                            } else {
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": пользователь не зарегистрирован");
                            }
                            send(request);
                        } catch (Exception e) {
                            System.out.println("Ошибка регистрации");
                        }
                    }
                    break;

                    case Authorization: {
                        try {
                            System.out.println(clientSocket.getInetAddress().toString().substring(1) + ": авторизация пользователя");
                            User user = (new JSON<User>()).fromJson(request.getRequestMessage(), User.class);
                            user = userService.checkAcount(user);
                            if (user == null) {
                                user=new User();
                                user.setId(0);
                                request.setRequestMessage((new JSON<User>()).toJson(user));
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": пользователь не авторизирован");
                                send(request);
                            } else {
                                request.setRequestMessage((new JSON<User>()).toJson(user));
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": пользователь авторизирован");
                                send(request);
                                request.setRequestMessage(new JSON<Storage>().toJsonArray(userService.getMany(user), Storage.class));
                                send(request);
                            }

                        } catch (Exception e) {
                            System.out.println("Ошибка авторизации");
                        }
                    }
                    break;
                    case CreateStorage: {
                        System.out.println((String.valueOf(clientSocket.getInetAddress()).substring(1) + ": добавление склада"));
                        Storage storage = (new JSON<Storage>()).fromJson(request.getRequestMessage(), Storage.class);
                        request.setRequestMessage(storageService.add(storage));
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад добавлен");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад  не добавлен");
                        }
                    }
                    break;
                    case DeleteStorage: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": удаление склада");
                        Storage storage = (new JSON<Storage>()).fromJson(request.getRequestMessage(), Storage.class);
                       request.setRequestMessage(storageService.delete(storage)+"");
                       send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад удален");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад не удален");
                        }


                    }
                    break;
                    case UpdateStorage: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": удаление склада");
                        Storage storage = (new JSON<Storage>()).fromJson(request.getRequestMessage(), Storage.class);
                        request.setRequestMessage(storageService.update(storage)+"");
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад изменен");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад не изменен");
                        }


                    }
                    break;
                    case Exit: {
                        Server.removeSocket(clientSocket);
                        clientSocket.close();
                    }
                    case GetProducts: {
                        try {
                            System.out.println(clientSocket.getInetAddress().toString().substring(1) + ": запрос на получение товаров");
                            Storage storage = (new JSON<Storage>()).fromJson(request.getRequestMessage(), Storage.class);

                            if (storage != null) {
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад найден");
                                request.setRequestMessage(new JSON<Product>().toJsonArray(storageService.getMany(storage), Product.class));
                                send(request);
                            }

                        } catch (Exception e) {
                            System.out.println("Ошибка получения товара");
                        }
                    }
                    break;
                    case GetCategory: {
                        try {
                            System.out.println(clientSocket.getInetAddress().toString().substring(1) + ": запрос на получение категорий");
                            Storage storage = (new JSON<Storage>()).fromJson(request.getRequestMessage(), Storage.class);

                            if (storage != null) {
                                System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": склад найден");
                                request.setRequestMessage(new JSON<Category>().toJsonArray(categoryService.getMany(storage),Category.class));
                                send(request);
                            }


                        } catch (Exception e) {
                            System.out.println("Ошибка получения категорий"+e);
                        }
                    }
                    break;
                    case CreateProduct: {
                        System.out.println((String.valueOf(clientSocket.getInetAddress()).substring(1) + ": добавление продукта"));
                       Product product = (new JSON<Product>()).fromJson(request.getRequestMessage(), Product.class);
                        request.setRequestMessage(productService.add(product));
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт добавлен");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт не добавлен");
                        }
                    }
                    break;
                    case CreateCategory: {
                        System.out.println((String.valueOf(clientSocket.getInetAddress()).substring(1) + ": добавление категории"));
                        Category category = (new JSON<Category>()).fromJson(request.getRequestMessage(), Category.class);
                        request.setRequestMessage(categoryService.add(category));
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория добавлена");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория не добавлена");
                        }
                    }
                    break;
                    case DeleteCategory: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": удаление категории");
                        Category category = (new JSON<Category>()).fromJson(request.getRequestMessage(), Category.class);
                        request.setRequestMessage(categoryService.delete(category)+"");
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория удалена");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория не удалена");
                        }


                    }
                    break;
                    case UpdateCategory: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": редактирование категории");
                        Category category = (new JSON<Category>()).fromJson(request.getRequestMessage(), Category.class);
                        request.setRequestMessage(categoryService.update(category)+"");
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория изменена");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": категория не изменилась");
                        }

                    }
                    break;
                    case DeleteProduct: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": удаление продукта");
                        Product product = (new JSON<Product>()).fromJson(request.getRequestMessage(), Product.class);
                        request.setRequestMessage(productService.delete(product)+"");
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт удален");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт не удален");
                        }


                    }
                    break;
                    case UpdateProduct: {
                        System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": изменение продукта");
                        Product product = (new JSON<Product>()).fromJson(request.getRequestMessage(), Product.class);
                        request.setRequestMessage(productService.update(product)+"");
                        send(request);
                        if (request.getRequestMessage().equals("true")) {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт изменен");

                        } else {
                            System.out.println(String.valueOf(clientSocket.getInetAddress()).substring(1) + ": продукт не изменился");
                        }

                    }
                    break;

                }
            }
        } catch (IOException e) {

        }


    }

}
