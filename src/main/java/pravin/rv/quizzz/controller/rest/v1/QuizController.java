package pravin.rv.quizzz.controller.rest.v1;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pravin.rv.quizzz.controller.utils.RestVerifier;
import pravin.rv.quizzz.model.AuthenticatedUser;
import pravin.rv.quizzz.model.CompletedTest;
import pravin.rv.quizzz.model.Question;
import pravin.rv.quizzz.model.Quiz;
import pravin.rv.quizzz.model.User;
import pravin.rv.quizzz.model.support.Response;
import pravin.rv.quizzz.model.support.Result;
import pravin.rv.quizzz.service.QuestionService;
import pravin.rv.quizzz.service.QuizService;
import pravin.rv.quizzz.service.TestCompltService;
import pravin.rv.quizzz.service.UserServiceImpl;


@RestController
@RequestMapping(QuizController.ROOT_MAPPING)
public class QuizController {

	public static final String ROOT_MAPPING = "/api/quizzes";
	
	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private TestCompltService testCompltService;

	@Autowired
	private UserServiceImpl userServiceImpl;


	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> findAll(Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean published) {
		
		if (published) {
			return quizService.findAllPublished(pageable);
		} else {
			return quizService.findAll(pageable);
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> searchAll(Pageable pageable, @RequestParam(required = true) String filter,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		return quizService.search(filter, pageable);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public Quiz save(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result) {

		logger.debug("The Quiz " + quiz.getName() + " is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return quizService.save(quiz, user.getUser());
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Quiz find(@PathVariable Long quiz_id) {

		return quizService.find(quiz_id);
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Quiz update(@PathVariable Long quiz_id, @Valid Quiz quiz, BindingResult result) {

		RestVerifier.verifyModelResult(result);

		quiz.setId(quiz_id);
		return quizService.update(quiz);
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.DELETE)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		quizService.delete(quiz);
	}

	@RequestMapping(value = "/{quiz_id}/questions", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public List<Question> findQuestions(@PathVariable Long quiz_id,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		Quiz quiz = quizService.find(quiz_id);

		if (onlyValid) {
			return questionService.findValidQuestionsByQuiz(quiz);
		} else {
			return questionService.findQuestionsByQuiz(quiz);
		}

	}

	@RequestMapping(value = "/{quiz_id}/publish", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public void publishQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		quizService.publishQuiz(quiz);
	}

	@RequestMapping(value = "/{quiz_id}/submitAnswers", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Result playQuiz(@PathVariable long quiz_id, @RequestBody List<Response> answersBundle) {
		Quiz quiz = quizService.find(quiz_id);
		return quizService.checkAnswers(quiz, answersBundle);
	}

	@RequestMapping(value = "/{quiz_id}/{correct_quest}/{total_quest}/submitTest", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public String save(@Valid CompletedTest completedTest,@PathVariable Integer quiz_id,@PathVariable Integer correct_quest,@PathVariable Integer total_quest) {
		System.out.println("*****#######"+quiz_id);
		
		User user = userServiceImpl.find((long) 3);
		completedTest.setTotalQuest(total_quest);
		completedTest.setCorrectQuest(correct_quest);
		completedTest.setQuizId(quiz_id);
		completedTest.setIsDone(true);
		completedTest.setUserId(3);
		completedTest.setCreatedBy(user.getUsername());
		completedTest.setCreatedDate(new Date()); 
		System.out.println("Pravin"+completedTest.toString());
		//RestVerifier.verifyModelResult(result);

		//Quiz quiz = quizService.find(quiz_id); 
		//question.setQuiz(quiz);

		testCompltService.save(completedTest);
		//"redirect:/editQuiz/" + newQuiz.getId();
		return "redirect:https://aws-quiz.com/";
	}

}
