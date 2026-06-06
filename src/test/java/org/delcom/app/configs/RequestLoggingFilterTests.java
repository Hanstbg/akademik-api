package org.delcom.app.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestLoggingFilterTests {

    @InjectMocks private RequestLoggingFilter filter;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;

    @Test
    void testDoFilter() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/api/auth/login");
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testInitAndDestroy() throws Exception {
        filter.init(null);
        filter.destroy();
    }
}
