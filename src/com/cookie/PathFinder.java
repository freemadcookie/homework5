package com.cookie;

import com.cookie.entity.DeliveryTask;
import com.cookie.entity.Route;
import com.cookie.entity.Transport;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class PathFinder {

    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        //необходимо для проверки вместимости груза
        double packageVol = deliveryTask.getVolume();
        //выбираем доступные пути доставки
        List<Route> routes = deliveryTask.getRoutes();
        //отфильтровываем доступный транспорт по доступным RouteType'ам, а также проверяем вмещается ли груз
        List<Transport> filteredTrans = getFilteredTrans(transports, routes, packageVol);

        //возвращаем оптимальный транспорт
        return getMinByPrice(filteredTrans, routes);
    }

    private List<Transport> getFilteredTrans(List<Transport> transports, List<Route> routes, double packageVol)
    {
        return transports.stream()
                .filter (transport -> routes.stream()
                        .anyMatch(route -> route.getType() == transport.getType() &&
                                packageVol<= transport.getVolume()))
                .collect(Collectors.toList());
    }

    //находит минимальный по затратам транспорт
    private Transport getMinByPrice(List<Transport> transports, List<Route> routes)
    {
        return transports.stream()
                .min(Comparator.comparing(transport -> transport.getPrice() *
                routes.stream().filter(route -> route.getType().equals(transport.getType())).findFirst().get().getLength()))
                .orElseThrow(() -> new RuntimeException("Не найдено"));
    }
}
