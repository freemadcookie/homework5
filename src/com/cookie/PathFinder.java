package com.cookie;

import com.cookie.entity.DeliveryTask;
import com.cookie.entity.Route;
import com.cookie.entity.Transport;

import java.util.*;
import java.util.stream.Collectors;


public class PathFinder {

    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        //���������� ��� �������� ����������� �����
        double packageVol = deliveryTask.getVolume();
        //�������� ��������� ���� ��������
        List<Route> routes = deliveryTask.getRoutes();

        //��������������� ��������� ��������� �� ��������� RouteType'��, � ����� ��������� ��������� �� ����
        List<Transport> filteredTrans =
                transports.stream()
                        .filter (transport -> routes.stream()
                                        .anyMatch(route -> route.getType() == transport.getType() &&
                                                packageVol<= transport.getVolume()))
                        .collect(Collectors.toList());

        //���������� ����������� ���������
        return getMinByPrice(filteredTrans);
    }

    //������� ����������� �� �������� ���������
    private Transport getMinByPrice(List<Transport> transports)
    {
        return transports
                .stream()
                .min(Comparator.comparing(Transport::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}
