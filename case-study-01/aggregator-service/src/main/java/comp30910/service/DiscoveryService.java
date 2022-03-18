package comp30910.service;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscoveryService {
    private final EurekaClient discoveryClient;
    private static final String SERVICE_PREFIX = "cinema-";
    private static final String SERVICE_SUFFIX = "-service";

    public List<String> getRegisteredServiceNames() {
        Stream<Application> registered = getRegisteredApplications();
        Stream<String> registeredLowerCase = registered.map(app -> app.getName().toLowerCase());
        List<String> serviceNames =
                registeredLowerCase
                        .filter(appName -> appName.startsWith(SERVICE_PREFIX))
                        .collect(Collectors.toList());
        return serviceNames;
    }

    public List<String> getCinemaUrlPrefixes() {
        List<String> serviceNames = getRegisteredServiceNames();
        return serviceNames.stream()
                .map(name -> name.replaceFirst(SERVICE_PREFIX, "").replaceFirst(SERVICE_SUFFIX, ""))
                .map(name -> "/cinema/" + name)
                .collect(Collectors.toList());
    }

    public Stream<Application> getRegisteredApplications() {
        return discoveryClient.getApplications().getRegisteredApplications().stream();
    }
}
