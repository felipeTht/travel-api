package com.klm.cases.df.config;

import java.io.IOException;
import java.util.Objects;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//@EnableWebMvc
@EnableScheduling
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	public static final String IGNORED_PATH = "/api";
    private static final String PATH_PATTERNS = "/**";
    private static final String INDEX_PAGE = "index.html";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new SinglePageAppResourceResolver());
    }

    private class SinglePageAppResourceResolver extends PathResourceResolver {


        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            var resource = location.createRelative(resourcePath);
            if (resourceExistsAndIsReadable(resource)) {
                //if the asked resource is index.html itself, we serve it with the base-href rewritten
                if (resourcePath.endsWith(INDEX_PAGE)) {
                    return location.createRelative(INDEX_PAGE);
                }
                //here we serve js, css, etc.
                return resource;
            }

            //do not serve a Resource on an ignored path
            if (("/" + resourcePath).startsWith(IGNORED_PATH)) {
                return null;
            }

            //we are in the case of an angular route here, we rewrite to index.html
            if (resourceExistsAndIsReadable(location.createRelative(INDEX_PAGE))) {
                return location.createRelative(INDEX_PAGE);
            }

            return null;
        }

        private boolean resourceExistsAndIsReadable(Resource resource) {
            Objects.requireNonNull(resource, "resource cannot be null");
            return resource.exists() && resource.isReadable();
        }
    }

}
