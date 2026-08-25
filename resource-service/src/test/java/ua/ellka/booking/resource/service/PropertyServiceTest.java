package ua.ellka.booking.resource.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ellka.booking.resource.dto.PropertyCreateReq;
import ua.ellka.booking.resource.dto.PropertyResp;
import ua.ellka.booking.resource.exception.NotFoundServiceException;
import ua.ellka.booking.resource.mapper.PropertyMapper;
import ua.ellka.booking.resource.model.Property;
import ua.ellka.booking.resource.model.PropertyType;
import ua.ellka.booking.resource.repo.PropertyRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepo propertyRepo;

    @Spy
    private PropertyMapper propertyMapper = Mappers.getMapper(PropertyMapper.class);

    @InjectMocks
    private PropertyService propertyService;

    private Property testProperty;
    private PropertyCreateReq testPropertyCreateReq;

    @BeforeEach
    void setUp() {
        testPropertyCreateReq = new PropertyCreateReq();
        testPropertyCreateReq.setName("Lux Apart");
        testPropertyCreateReq.setDescription("This is bets apart in London for tourists");
        testPropertyCreateReq.setLocation("London");
        testPropertyCreateReq.setPrice(BigDecimal.valueOf(199));
        testPropertyCreateReq.setType(PropertyType.APARTMENT);
        testPropertyCreateReq.setMaxGuests(4);

        testProperty = new Property();
        testProperty.setId(1L);
        testProperty.setName(testPropertyCreateReq.getName());
        testProperty.setDescription(testPropertyCreateReq.getDescription());
        testProperty.setLocation(testPropertyCreateReq.getLocation());
        testProperty.setPrice(testPropertyCreateReq.getPrice());
        testProperty.setType(testPropertyCreateReq.getType());
        testProperty.setMaxGuests(testPropertyCreateReq.getMaxGuests());
        testProperty.setActive(true);
    }

    @Test
    void createPropertyTest_success() {

        when(propertyRepo.save(any(Property.class))).thenReturn(testProperty);

        PropertyResp propertyResp = propertyService.create(testPropertyCreateReq);

        assertNotNull(propertyResp);
        assertEquals(testProperty.getId(), propertyResp.getId());
        assertEquals(testProperty.getName(), propertyResp.getName());
        assertEquals(testProperty.getDescription(), propertyResp.getDescription());
        assertEquals(testProperty.getLocation(), propertyResp.getLocation());
        assertEquals(testProperty.getPrice(), propertyResp.getPrice());
        assertEquals(testProperty.getType(), propertyResp.getType());
        assertEquals(testProperty.getMaxGuests(), propertyResp.getMaxGuests());
        assertEquals(0.0, propertyResp.getAverageRating());
        assertNull(propertyResp.getReviewCount());
    }

    @Test
    void findByIdTest_success() {
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.of(testProperty));
        PropertyResp propertyResp = propertyService.findById(testProperty.getId());

        assertNotNull(propertyResp);
        assertEquals(testProperty.getId(), propertyResp.getId());
        assertEquals(testProperty.getName(), propertyResp.getName());
        assertEquals(testProperty.getDescription(), propertyResp.getDescription());
        assertEquals(testProperty.getLocation(), propertyResp.getLocation());
        assertEquals(testProperty.getPrice(), propertyResp.getPrice());
        assertEquals(testProperty.getType(), propertyResp.getType());
        assertEquals(testProperty.getMaxGuests(), propertyResp.getMaxGuests());
        assertEquals(0.0, propertyResp.getAverageRating());
        assertNull(propertyResp.getReviewCount());
    }

    @Test
    void findByIdTest_whenIdNotFound() {
        Long propertyId = 9999L;
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundServiceException exception = assertThrows(NotFoundServiceException.class,
                () -> propertyService.findById(propertyId));
        assertEquals("Property with id " + propertyId + " not found", exception.getMessage());
    }

    @Test
    void findByIdTest_whenPropertyDeactivated() {
        testProperty.setActive(false);
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.of(testProperty));

        NotFoundServiceException exception = assertThrows(NotFoundServiceException.class,
                () -> propertyService.findById(testProperty.getId()));
        assertEquals("Property with id " + testProperty.getId() + " not found", exception.getMessage());
    }

    @Test
    void findAllTest_success() {
        when(propertyRepo.findAllByActive(true)).thenReturn(List.of(testProperty));
        List<PropertyResp> all = propertyService.findAll();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals(testProperty.getId(), all.getFirst().getId());
    }

    @Test
    void findAllTest_whenNoActiveProperties() {
        when(propertyRepo.findAllByActive(true)).thenReturn(List.of());

        List<PropertyResp> result = propertyService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByLocationTest_success() {
        String location = "London";
        when(propertyRepo.findAllByLocationIgnoreCaseAndActive(location, true)).thenReturn(List.of(testProperty));
        List<PropertyResp> allByLocation = propertyService.findAllByLocation(location);

        assertNotNull(allByLocation);
        assertEquals(1, allByLocation.size());
        assertEquals(testProperty.getId(), allByLocation.getFirst().getId());
        assertEquals(testProperty.getLocation(), allByLocation.getFirst().getLocation());
    }

    @Test
    void findAllByLocationTest_whenLocationIsBlank() {
        String location = " ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> propertyService.findAllByLocation(location));
        assertEquals("Location is blank", exception.getMessage());
    }

    @Test
    void findAllByLocationTest_whenPropertyNoActive() {
        String location = "London";
        when(propertyRepo.findAllByLocationIgnoreCaseAndActive(location, true)).thenReturn(List.of());
        List<PropertyResp> allByLocation = propertyService.findAllByLocation(location);
        assertNotNull(allByLocation);
        assertTrue(allByLocation.isEmpty());
    }

    @Test
    void deactivateTest_success() {
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.of(testProperty));
        PropertyResp deactivate = propertyService.deactivate(testProperty.getId());

        assertNotNull(deactivate);
        assertEquals(testProperty.getId(), deactivate.getId());
        assertEquals(testProperty.getName(), deactivate.getName());
        assertEquals(testProperty.getDescription(), deactivate.getDescription());
        assertEquals(testProperty.getLocation(), deactivate.getLocation());
        assertEquals(testProperty.getPrice(), deactivate.getPrice());
        assertEquals(testProperty.getType(), deactivate.getType());
        assertEquals(testProperty.getMaxGuests(), deactivate.getMaxGuests());
        assertEquals(0.0, deactivate.getAverageRating());
        assertNull(deactivate.getReviewCount());
    }

    @Test
    void deactivateTest_whenIdNotFound() {
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundServiceException exception = assertThrows(NotFoundServiceException.class,
                () -> propertyService.deactivate(testProperty.getId()));
        assertEquals("Property with id " + testProperty.getId() + " not found", exception.getMessage());
    }

    @Test
    void deactivateTest_whenPropertyIsNotActive() {
        testProperty.setActive(false);
        when(propertyRepo.findById(anyLong())).thenReturn(Optional.of(testProperty));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> propertyService.deactivate(testProperty.getId()));
        assertEquals("Property is already deactivated", exception.getMessage());
    }

}