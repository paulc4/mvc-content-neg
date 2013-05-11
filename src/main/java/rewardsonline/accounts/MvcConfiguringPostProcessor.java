package rewardsonline.accounts;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * The HTTP Message converters are created automatically by Spring. To perform
 * additional configuration we use a bean post-processor.
 */
public class MvcConfiguringPostProcessor implements BeanPostProcessor {

	private Logger logger = Logger.getLogger(MvcConfiguringPostProcessor.class);

	/**
	 * Enable pretty print on any bean of type
	 * {@link MappingJacksonHttpMessageConverter} or
	 * {@link MappingJackson2HttpMessageConverter}.
	 */
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		if (bean instanceof HttpMessageConverter<?>)
			logger.warn("HTTP Converter: " + bean.getClass());

		if (bean instanceof MappingJacksonHttpMessageConverter) {
			logger.info("Configuring Jackson V1: " + name);
			((MappingJacksonHttpMessageConverter) bean).setPrettyPrint(true);
		} else if (bean instanceof MappingJackson2HttpMessageConverter) {
			logger.info("Configuring Jackson V2: " + name);
			((MappingJackson2HttpMessageConverter) bean).setPrettyPrint(true);
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// Nothing to do
		return bean;
	}

}