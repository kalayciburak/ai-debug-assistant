# AI Debug Assistant

AI Debug Assistant is a Spring Boot library designed to enhance the debugging process for developers by providing detailed
error explanations and possible solutions using AI, specifically leveraging the GroqCloud API.

## Features

- **Error Explanation**: Automatically explains exceptions thrown in Spring Boot applications using AI.
- **Multi-Language Support**: Provides error explanations in multiple languages, ensuring that developers around the world
  can understand and resolve issues faster.
- **Easy Integration**: Designed as a drop-in library for existing Spring Boot applications.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- Spring Boot 3.3.0 or higher

### Installation

1. Add the dependency to your Spring Boot project:

For Maven, add the following to your `pom.xml`:

```xml

<dependency>
    <groupId>com.kalayciburak</groupId>
    <artifactId>ai-debug-assistant</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

2. Configure your `application.yml` to include your API key and other necessary settings:

```yaml
api:
  model: llama3-groq-70b-8192-tool-use-preview
  base-url: https://api.groq.com/openai
  key: your-api-key-here
  response:
    language: en
```

### Usage

After installing and configuring the library, it will automatically handle exceptions thrown within your Spring Boot
controllers and provide AI-generated explanations and solutions.

Here's an example of a basic Spring Boot application setup:

```java
@SpringBootApplication(scanBasePackages = "com.kalayciburak")
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}
```

## Example Output

When an exception is thrown in your Spring Boot application, the AI Debug Assistant will intercept the exception and provide
a detailed explanation and possible solutions. The output is formatted in a structured JSON format, providing comprehensive details about the exception encountered. This structured output is made possible by a global exception handler integrated within the project, which formats error messages and associated information neatly. Here's an example of the output:

```json
{
  "timestamp": "10-09-2024 23:11:27",
  "type": "ERROR: ENTITY_NOT_FOUND_EXCEPTION",
  "code": "2900",
  "message": "Herhangi bir kayıt bulunamadı.",
  "success": false,
  "status": "NOT_FOUND",
  "detail": {
    "className": "jdk.internal.reflect.DirectConstructorHandleAccessor",
    "methodName": "newInstance",
    "lineNumber": 62,
    "debugMessage": "Original Error Message: 'Herhangi bir kategori bulunamadı.'\n\nExplanation in Turkish: Bu hata, kategori bulunamadığı anlamına gelir.\n\nDetails in Turkish:\n- Hata Mesajı: 'Herhangi bir kategori bulunamadı.'\n- Açıklama: Bu hata, kategori bulunamadığı anlamına gelir.\n\nPossible Causes and Solutions in Turkish:\n- Kategori ismi yanlış yazılmış olabilir. Çözüm: Kategori ismini doğru yazmaya dikkat etmek önemlidir.\n- Kategori veritabanında kaydedilmemiş olabilir. Çözüm: Kategorinin veritabanına kaydedildiğini kontrol etmek önemlidir.\n- Kategori silinmiş olabilir. Çözüm: Kategorinin silindiğini kontrol etmek önemlidir ve eğer öyleyse, onu tekrar oluşturmak gerekir."
  }
}
```

## How It Works

The AI Debug Assistant intercepts exceptions thrown from your controllers, sends a request to the OpenAI API with the error
message, and receives a detailed explanation and suggested solutions, which are then logged or can be used further based on
your exception handling strategy.

## Contribution

Contributions are welcome! For major changes, please open an issue first to discuss what you would like to change.
