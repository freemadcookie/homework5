package com.cookie;

import com.cookie.entity.DeliveryTask;
import com.cookie.entity.Route;
import com.cookie.entity.Transport;

import java.util.*;
import java.util.stream.Collectors;


public class PathFinder {

    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        //необходимо для проверки вместимости груза
        double packageVol = deliveryTask.getVolume();
        //выбираем доступные пути доставки
        List<Route> routes = deliveryTask.getRoutes();

        //отфильтровываем доступный транспорт по доступным RouteType'ам, а также проверяем вмещается ли груз
        List<Transport> filteredTrans =
                transports.stream()
                        .filter (transport -> routes.stream()
                                        .anyMatch(route -> route.getType() == transport.getType() &&
                                                packageVol<= transport.getVolume()))
                        .collect(Collectors.toList());

        //возвращаем оптимальный транспорт
        return getMinByPrice(filteredTrans);
    }

    //находит минимальный по затратам транспорт
    private Transport getMinByPrice(List<Transport> transports)
    {
        return transports
                .stream()
                .min(Comparator.comparing(Transport::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}
